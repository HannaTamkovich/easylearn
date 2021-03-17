package com.easylearn.easylearn.word.service;

import com.easylearn.easylearn.category.repository.CategoryRepository;
import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.core.exception.ServiceException;
import com.easylearn.easylearn.handbook.russianword.repository.RussianWordRepository;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.service.UserAccountService;
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
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final UserAccountService userAccountService;

    private final WordParamConverter wordParamConverter;
    private final WordEntityConverter wordEntityConverter;

    private final Sort defaultSort = Sort.by(WordEntity_.WORD).ascending();

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
    @NotNull
    @Transactional(readOnly = true)
    public Collection<Card> findAllCards(@NotNull WordFilter wordFilter) {
        log.info("Find cards by filter");

        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());
        wordFilter.setUserId(currentUser.getId());
        wordFilter.setLanguages(List.of(currentUser.getLanguage()));

        //TODO сортировать по дате последнего ответа
        var spec = WordSpecMaker.makeSpec(wordFilter);
        var wordEntities = wordRepository.findAll(spec);
        var words = wordEntityConverter.toModels(wordEntities);

        return convertToCard(words);
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
                    return wordRepository.save(wordEntityConverter.toEntity(word));
                });

        addToMyWords(wordEntity.getId());

        log.debug("Word has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull WordParam wordParam) {
        log.info("Update word ({})", id);

        var word = findById(id);
        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());

        if (StringUtils.equals(word.getWord(), wordParam.getWord()) &&
                StringUtils.equals(word.getTranslation(), wordParam.getTranslation()) &&
                word.getLanguage() == currentUser.getLanguage()) {
            log.info("None of the fields were updated");
            return;
        }

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

        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());
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

    @Override
    @Transactional
    public void addToCategory(@NotNull Long id, @NotNull Long categoryId) {
        log.info("Add word ({}) to category ({})", id, categoryId);

        var categoryEntity = getCategoryEntity(categoryId);
        validateWordLanguageToAddToCategory(id, categoryEntity);

        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());

        var wordToUserEntity = wordToUserRepository.findByWordIdAndUserId(id, currentUser.getId()).orElseThrow();
        wordToUserEntity.setCategory(categoryEntity);

        wordToUserRepository.save(wordToUserEntity);

        log.debug("Word has been added to category");
    }

    @Override
    @Transactional
    public void removeFromCategory(@NotNull Long id, @NotNull Long categoryId) {
        log.info("Remove word ({}) from category ({})", id, categoryId);

        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());

        var wordToUserEntity = wordToUserRepository.findByWordIdAndUserIdAndCategory_Id(id, currentUser.getId(), categoryId);
        wordToUserEntity.setCategory(null);

        wordToUserRepository.save(wordToUserEntity);

        log.debug("Word has been removed from category");
    }

    @NotNull
    @Override
    @Transactional
    public boolean answer(@NotNull Long id, @NotBlank String selectedValue) {
        log.info("Answer word {}", id);

        /*var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());
        var wordToUserEntity = wordToUserRepository.findByWordIdAndUserId(id, currentUser.getId()).orElseThrow();

        if (isCorrectAnswer) {
            wordToUserEntity.setNumberOfCorrectAnswers(wordToUserEntity.getNumberOfCorrectAnswers() + 1);
        }
        wordToUserEntity.setNumberOfAnswers(wordToUserEntity.getNumberOfAnswers() + 1);
        wordToUserEntity.setDateOfLastAnswer(Instant.now());

        wordToUserRepository.save(wordToUserEntity);*/

        log.debug("Word has been answered");
        return true;

    }

    private Collection<Card> convertToCard(Collection<Word> words) {
        return words.stream()
                .map(it -> {
                    var randomWords = Set.of(
                            russianWordRepository.getOne(3491911L).getWord(),
                            russianWordRepository.getOne(62L).getWord(),
                            russianWordRepository.getOne(4098636L).getWord(),
                            it.getTranslation()
                    );
                    return Card.builder()
                            .id(it.getId())
                            .word(it.getWord())
                            .translations(randomWords)
                            .category(it.getCategory())
                            .build();
                }).collect(Collectors.toSet());
    }

    private void deleteFromUserList(Long wordId, UserAccount currentUser) {
        wordToUserRepository.deleteByUserIdAndWordId(currentUser.getId(), wordId);
    }

    private void deletePermanently(Long wordId) {
        wordToUserRepository.deleteByWordId(wordId);
        wordRepository.deleteById(wordId);
    }

    private Word updateFields(Word word, WordParam wordParam) {
        word.setWord(wordParam.getWord());
        word.setTranslation(wordParam.getTranslation());
        return word;
    }

    private CategoryEntity getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class.getSimpleName(), categoryId));
    }

    private void validatingForDuplication(Long wordId, UserAccount currentUser) {
        if (wordToUserRepository.existsByUserIdAndWordId(currentUser.getId(), wordId)) {
            throw new DuplicateException("Слово было добавлено ранее.");
        }
    }

    private void validateWordForDeleting(Long wordId, UserAccount currentUser) {
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

    private void addToMyWords(@NotNull Long id) {
        log.info("Add word ({}) to current user", id);

        var currentUser = userAccountService.loadByUsername(currentUserService.getUsername());
        validatingForDuplication(id, currentUser);

        var wordToUserEntity = WordToUserEntity.builder()
                .wordId(id)
                .userId(currentUser.getId())
                .numberOfAnswers(0L)
                .numberOfCorrectAnswers(0L)
                .dateOfLastAnswer(Instant.now())
                .build();
        wordToUserRepository.save(wordToUserEntity);

        log.debug("Word has been added");
    }
}
