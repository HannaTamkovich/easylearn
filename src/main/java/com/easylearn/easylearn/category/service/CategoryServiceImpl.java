package com.easylearn.easylearn.category.service;

import com.easylearn.easylearn.category.dto.CategoryParam;
import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.repository.CategoryRepository;
import com.easylearn.easylearn.category.repository.converter.CategoryEntityConverter;
import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import com.easylearn.easylearn.category.service.converter.CategoryParamConverter;
import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.core.exception.ServiceException;
import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.service.UserService;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import com.easylearn.easylearn.word.service.WordService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final WordToUserRepository wordToUserRepository;

    private final CurrentUserService currentUserService;
    private final UserService userService;

    private final CategoryEntityConverter categoryEntityConverter;
    private final CategoryParamConverter categoryParamConverter;

    private final WordService wordService;

    @Override
    @Transactional(readOnly = true)
    @NotNull
    public Category findById(@NotNull Long id) {
        log.info("Find category by id ({})", id);
        return categoryRepository.findById(id)
                .map(categoryEntityConverter::toModel)
                .orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class.getSimpleName(), id));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Collection<Category> findAllForCurrentUser() {
        log.info("Find all categories for user");
        var currentUserUsername = currentUserService.getUsername();
        var categoryEntities = categoryRepository.findAllByUser_UsernameOrderByName(currentUserUsername);
        return categoryEntityConverter.toModels(categoryEntities);
    }

    @Override
    @Transactional
    public void create(@NotNull CategoryParam categoryParam) {
        log.info("Create category");

        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        var currentUserLanguage = currentUser.getLanguage();
        validatingForDuplication(categoryParam, currentUserLanguage);

        var category = categoryParamConverter.toModel(categoryParam);
        category.setLanguage(currentUserLanguage);
        category.setUser(currentUser);
        var savedCategory = categoryRepository.save(categoryEntityConverter.toEntity(category));

        addWordsToCategory(savedCategory, categoryParam);

        log.debug("Category has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull CategoryParam categoryParam) {
        log.info("Update category");

        var categoryToUpdate = findById(id);
        validateToUpdate(categoryToUpdate, categoryParam);

        categoryToUpdate.setName(categoryParam.getName());
        var savedCategory = categoryRepository.save(categoryEntityConverter.toEntity(categoryToUpdate));

        addWordsToCategory(savedCategory, categoryParam);

        log.debug("Category has been updated");
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        log.info("Delete category");

        validateToDelete(id);
        categoryRepository.deleteById(id);

        log.debug("Category has been deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findByWordIdForCurrentUser(Long wordId) {
        var currentUser = userService.loadByUsername(currentUserService.getUsername());

        return wordToUserRepository.findEntityByWordIdAndUserId(wordId, currentUser.getId())
                .flatMap(wordToUserEntity -> Optional.ofNullable(wordToUserEntity.getCategory())
                        .map(categoryEntityConverter::toModel));
    }

    private void validatingForDuplication(CategoryParam categoryParam, Language currentUserLanguage) {
        if (categoryRepository.existsByNameAndLanguage(categoryParam.getName(), currentUserLanguage)) {
            throw new DuplicateException("Данная категория уже существует");
        }
    }

    private void validateToUpdate(Category category, CategoryParam categoryParam) {
        if (categoryRepository.existsByNameAndLanguageAndIdNot(categoryParam.getName(), category.getLanguage(), category.getId())) {
            throw new DuplicateException("Category already exists");
        }
    }

    private void validateToDelete(Long id) {
        if (wordToUserRepository.existsByCategory_Id(id)) {
            throw new ServiceException("Category has words. Delete them before category deletion");
        }
    }

    private void addWordsToCategory(CategoryEntity savedCategory, CategoryParam categoryParam) {
        var wordIds = categoryParam.getWordIds();

        var existedWordIds = wordService.findAllByCategory(savedCategory.getId()).stream()
                .map(Word::getId).collect(Collectors.toSet());

        if (!CollectionUtils.isEqualCollection(existedWordIds, wordIds)) {
            wordIds.forEach(it -> {
                if (!existedWordIds.contains(it)) {
                    wordService.addToCategory(it, savedCategory.getId());
                }
            });
            existedWordIds.forEach(it -> {
                if (!wordIds.contains(it)) {
                    wordService.deleteFromCategory(it, savedCategory.getId());
                }
            });
        }
    }
}
