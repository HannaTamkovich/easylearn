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
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

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
        categoryRepository.save(categoryEntityConverter.toEntity(category));

        log.debug("Category has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull CategoryParam categoryParam) {
        log.info("Update category");

        var categoryToUpdate = findById(id);
        validateToUpdate(categoryToUpdate, categoryParam);

        categoryToUpdate.setName(categoryParam.getName());
        categoryRepository.save(categoryEntityConverter.toEntity(categoryToUpdate));

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

    private void validatingForDuplication(CategoryParam categoryParam, Language currentUserLanguage) {
        if (categoryRepository.existsByNameAndLanguage(categoryParam.getName(), currentUserLanguage)) {
            throw new DuplicateException("Category already exists");
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
}
