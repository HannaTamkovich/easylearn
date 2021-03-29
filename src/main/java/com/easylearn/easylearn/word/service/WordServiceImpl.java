package com.easylearn.easylearn.word.service;

import com.easylearn.easylearn.category.repository.CategoryRepository;
import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import com.easylearn.easylearn.core.dto.response.PageResult;
import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.core.exception.ServiceException;
import com.easylearn.easylearn.handbook.russianword.repository.RussianWordRepository;
import com.easylearn.easylearn.handbook.russianword.repository.entity.RussianWordEntity;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.service.UserService;
import com.easylearn.easylearn.word.dto.CardFilter;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.model.Card;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.repository.WordRepository;
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import com.easylearn.easylearn.word.repository.converter.WordEntityConverter;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import com.easylearn.easylearn.word.repository.entity.WordEntity_;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity_;
import com.easylearn.easylearn.word.repository.specification.CardSpecMaker;
import com.easylearn.easylearn.word.repository.specification.WordSpecMaker;
import com.easylearn.easylearn.word.service.converter.WordParamConverter;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final CategoryRepository categoryRepository;
    private final WordToUserRepository wordToUserRepository;
    private final RussianWordRepository russianWordRepository;

    private final CurrentUserService currentUserService;
    private final UserService userService;

    private final WordParamConverter wordParamConverter;
    private final WordEntityConverter wordEntityConverter;

    private final Sort defaultSort = Sort.by(WordEntity_.WORD).ascending();
    private final Sort defaultSortUserWords = Sort.by(WordToUserEntity_.DATE_OF_LAST_ANSWER).ascending();

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Word findById(@NotNull Long id) {
        log.info("Find word by id ({})", id);

        return wordRepository.findById(id)
                .map(wordEntityConverter::toModel)
                .orElseThrow(() -> new EntityNotFoundException(WordEntity.class.getSimpleName(), id));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Collection<Word> findAll(@NotNull WordFilter wordFilter) {
        log.info("Find word by filter");

        var spec = WordSpecMaker.makeSpec(wordFilter);
        var wordEntities = wordRepository.findAll(spec, defaultSort);

        return wordEntityConverter.toModels(wordEntities);
    }

    @Override
    public Collection<Word> findAllByCategory(Long categoryId) {
        log.info("Find word by categoryId {}", categoryId);

        var filter = new WordFilter(categoryId, currentUserService.getUsername());

        return findAll(filter);
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public PageResult<Card> findAllCards(@NotNull CardFilter cardFilter) {
        log.info("Find cards by filter");

        var userId = cardFilter.isOnlyUserWords() ? userService.loadByUsername(currentUserService.getUsername()).getId() : null;
        var spec = CardSpecMaker.makeSpec(userId);
        var wordEntities = wordRepository.findAll(spec);
        var words = wordEntityConverter.toModels(wordEntities).stream()
                .collect(Collectors.toMap(Word::getId, Function.identity()));

        var wordToUserEntities = Optional.ofNullable(userId)
                .map(wordToUserRepository::findAllByUserIdOrderByDateOfLastAnswerAsc)
                .orElseGet(() -> wordToUserRepository.findAll(defaultSortUserWords));

        var sortedWords = wordToUserEntities.stream().map(it -> words.get(it.getId())).collect(Collectors.toList());

        return convertToCard(sortedWords, cardFilter);
    }

    @Override
    @Transactional
    public void create(@NotNull WordParam wordParam) {
        log.info("Create word");

        var userLanguage = currentUserService.getLanguage();
        var wordEntity = wordRepository.findByWordAndTranslationAndLanguage(wordParam.getWord(), wordParam.getTranslation(), userLanguage)
                .orElseGet(() -> {
                    var word = wordParamConverter.toModel(wordParam);
                    word.setLanguage(userLanguage);
                    var savedWord = wordRepository.save(wordEntityConverter.toEntity(word));
                    addWordToDictionary(savedWord);
                    return savedWord;
                });

        addToUserWords(wordEntity.getId());
        Optional.ofNullable(wordParam.getCategoryId())
                .ifPresent(it -> addToCategory(wordEntity.getId(), it));

        log.debug("Word has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull WordParam wordParam) {
        log.info("Update word ({})", id);

        var word = findById(id);
        var currentUser = userService.loadByUsername(currentUserService.getUsername());

        var countByWordId = wordToUserRepository.countByWordId(id);
        if (countByWordId == 1) {
            var updatedWord = updateFields(word, wordParam);
            wordRepository.save(wordEntityConverter.toEntity(updatedWord));

        } else if (countByWordId > 1) {
            create(wordParam);
            deleteFromUserList(id, currentUser);

        } else {
            throw new EntityNotFoundException(WordEntity.class.getSimpleName(), id);
        }

        log.debug("Word has been updated");
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        log.info("Delete word ({})", id);

        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        validateWordForDeleting(id, currentUser);

        var countByWordId = wordToUserRepository.countByWordId(id);
        if (countByWordId == 1) {
            deletePermanently(id);

        } else if (countByWordId > 1) {
            deleteFromUserList(id, currentUser);

        } else {
            throw new EntityNotFoundException(WordEntity.class.getSimpleName(), id);
        }

        log.debug("Word has been deleted");
    }

    @NotNull
    @Override
    @Transactional
    public boolean answer(@NotNull Long id, @NotBlank String selectedValue) {
        log.info("Answer word {}", id);

        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        var wordToUserEntity = wordToUserRepository.findByWordIdAndUserId(id, currentUser.getId());
        var word = findById(id);

        var isCorrectTranslation = StringUtils.equalsIgnoreCase(word.getTranslation(), selectedValue);

        if (isCorrectTranslation) {
            wordToUserEntity.setNumberOfCorrectAnswers(wordToUserEntity.getNumberOfCorrectAnswers() + 1);
        }
        wordToUserEntity.setNumberOfAnswers(wordToUserEntity.getNumberOfAnswers() + 1);
        wordToUserEntity.setDateOfLastAnswer(Instant.now());

        wordToUserRepository.save(wordToUserEntity);

        log.debug("Word has been answered");

        return isCorrectTranslation;
    }

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public Collection<Word> findAllWithEmptyCategory() {
        var currentUser = userService.loadByUsername(currentUserService.getUsername());

        var spec = WordSpecMaker.makeSpecForFreeWords(currentUser.getId());
        var wordEntities = wordRepository.findAll(spec, defaultSort);

        return wordEntityConverter.toModels(wordEntities);
    }

    @Override
    @Transactional
    public void addToCategory(@NotNull Long id, @NotNull Long categoryId) {
        var categoryEntity = getCategoryEntity(categoryId);
        validateWordLanguageToAddToCategory(id, categoryEntity);

        var currentUser = userService.loadByUsername(currentUserService.getUsername());

        var wordToUserEntity = wordToUserRepository.findEntityByWordIdAndUserId(id, currentUser.getId()).orElseThrow();
        wordToUserEntity.setCategory(categoryEntity);

        wordToUserRepository.save(wordToUserEntity);
    }

    @Override
    @Transactional
    public void deleteFromCategory(@NotNull Long id, @NotNull Long categoryId) {
        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        var wordToUserEntity = wordToUserRepository.
                findEntityByWordIdAndUserIdAndCategory_Id(id, currentUser.getId(), categoryId).orElseThrow();

        removeFromCategory(wordToUserEntity);
    }

    @Override
    @Transactional
    public void addToUserWords(@NotNull Long id) {
        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        validatingForDuplication(id, currentUser);

        var wordToUserEntity = WordToUserEntity.builder()
                .wordId(id)
                .userId(currentUser.getId())
                .numberOfAnswers(0L)
                .numberOfCorrectAnswers(0L)
                .dateOfLastAnswer(Instant.now())
                .build();
        wordToUserRepository.save(wordToUserEntity);
    }

    private PageResult<Card> convertToCard(List<Word> words, CardFilter cardFilter) {
        var content = words.stream()
                .map(it -> {
                    var randomWords = Set.of(
                            russianWordRepository.findRussianWord(it.getTranslation()).getWord().toLowerCase(),
                            russianWordRepository.findRussianWord(it.getTranslation()).getWord().toLowerCase(),
                            russianWordRepository.findRussianWord(it.getTranslation()).getWord().toLowerCase(),
                            it.getTranslation().toLowerCase()
                    );
                    return Card.builder()
                            .id(it.getId())
                            .word(it.getWord())
                            .translations(randomWords)
                            .build();
                }).collect(Collectors.toSet());

        var pageNumber = cardFilter.getPageNumber();
        var pageSize = cardFilter.getPageSize();
        var totalElements = words.size();
        var totalPages = BigDecimal.valueOf(totalElements).divide(BigDecimal.valueOf(pageSize), RoundingMode.UP).toBigInteger().intValue();

        return new PageResult<>(content, totalPages, totalElements, pageNumber);
    }

    private void addWordToDictionary(WordEntity savedWord) {
        if (!russianWordRepository.existsByWord(savedWord.getTranslation())) {
            russianWordRepository.save(RussianWordEntity.builder().word(savedWord.getWord()).build());
        }
    }

    public void updateCategory(Word word, WordParam wordParam) {
        var currentUser = userService.loadByUsername(currentUserService.getUsername());

        var wordToUserEntity = wordToUserRepository.findByWordIdAndUserId(word.getId(), currentUser.getId());

        if (Objects.nonNull(wordToUserEntity.getCategory()) && Objects.isNull(wordParam.getCategoryId())) {
            removeFromCategory(wordToUserEntity);

        } else if (Objects.isNull(wordToUserEntity.getCategory()) && Objects.nonNull(wordParam.getCategoryId())) {
            addToCategory(word.getId(), wordParam.getCategoryId());

        } else {
            removeFromCategory(wordToUserEntity);
            addToCategory(word.getId(), wordParam.getCategoryId());
        }
    }

    public void removeFromCategory(WordToUserEntity wordToUserEntity) {
        wordToUserEntity.setCategory(null);

        wordToUserRepository.save(wordToUserEntity);
    }

    private void deleteFromUserList(Long wordId, User currentUser) {
        wordToUserRepository.deleteByUserIdAndWordId(currentUser.getId(), wordId);
    }

    private void deletePermanently(Long wordId) {
        wordToUserRepository.deleteByWordId(wordId);
        wordRepository.deleteById(wordId);
    }

    private Word updateFields(Word word, WordParam wordParam) {
        word.setWord(wordParam.getWord());
        word.setTranslation(wordParam.getTranslation());
        updateCategory(word, wordParam);
        return word;
    }

    private CategoryEntity getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class.getSimpleName(), categoryId));
    }

    private void validatingForDuplication(Long wordId, User currentUser) {
        if (wordToUserRepository.existsByUserIdAndWordId(currentUser.getId(), wordId)) {
            throw new DuplicateException("Слово было добавлено ранее.");
        }
    }

    private void validateWordForDeleting(Long wordId, User currentUser) {
        if (!wordToUserRepository.existsByUserIdAndWordId(currentUser.getId(), wordId)) {
            throw new ServiceException("Слово не было найдено в Вашем списке слов.");
        }
    }

    private void validateWordLanguageToAddToCategory(Long id, CategoryEntity categoryEntity) {
        var wordLanguage = findById(id).getLanguage();
        if (!Objects.equals(wordLanguage, categoryEntity.getLanguage())) {
            throw new ServiceException("Слово и категория имеют различные языки.");
        }
    }
}
