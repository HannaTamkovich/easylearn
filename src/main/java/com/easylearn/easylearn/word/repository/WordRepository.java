package com.easylearn.easylearn.word.repository;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long>, JpaSpecificationExecutor<WordEntity> {

    Optional<WordEntity> findByWordIgnoreCaseAndTranslationIgnoreCaseAndLanguage(String word, String translation, Language language);

    Collection<WordEntity> findByLanguage(Language language);
}
