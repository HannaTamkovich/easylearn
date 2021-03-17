package com.easylearn.easylearn.category.repository;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import com.easylearn.easylearn.language.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByNameAndLanguage(String name, Language language);

    boolean existsByNameAndLanguageAndIdNot(String name, Language language, Long id);

    Collection<CategoryEntity> findAllByUserAccount_Username(String username);
}
