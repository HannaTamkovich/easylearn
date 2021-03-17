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
import com.easylearn.easylearn.security.service.CurrentUserService;
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
        var categoryEntities = categoryRepository.findAllByUserAccount_Username(currentUserUsername);
        return categoryEntityConverter.toModels(categoryEntities);
    }

    @Override
    @Transactional
    public void create(@NotNull CategoryParam categoryParam) {
        log.info("Create category");

        validatingForDuplication(categoryParam);

        var category = categoryParamConverter.toModel(categoryParam);
        categoryRepository.save(categoryEntityConverter.toEntity(category));

        log.debug("Category has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull CategoryParam categoryParam) {
        log.info("Update category");

        validateToUpdate(id, categoryParam);

        var categoryToUpdate = findById(id);
        updateFields(categoryToUpdate, categoryParam);
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

    private void validatingForDuplication(CategoryParam categoryParam) {
        if (categoryRepository.existsByNameAndLanguage(categoryParam.getName(), categoryParam.getLanguage())) {
            throw new DuplicateException("Category already exists");
        }
    }

    private void validateToUpdate(Long id, CategoryParam categoryParam) {
        if (categoryRepository.existsByNameAndLanguageAndIdNot(categoryParam.getName(), categoryParam.getLanguage(), id)) {
            throw new DuplicateException("Category already exists");
        }
    }

    private void validateToDelete(Long id) {
        if (wordToUserRepository.existsByCategory_Id(id)) {
            throw new ServiceException("Category has words. Delete them before category deletion");
        }
    }

    private void updateFields(Category categoryToUpdate, CategoryParam categoryParam) {
        categoryToUpdate.setName(categoryParam.getName());
        categoryToUpdate.setLanguage(categoryParam.getLanguage());
    }
}
