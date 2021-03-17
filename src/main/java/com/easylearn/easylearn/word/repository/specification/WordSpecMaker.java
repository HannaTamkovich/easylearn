package com.easylearn.easylearn.word.repository.specification;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity_;
import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import com.easylearn.easylearn.word.repository.entity.WordEntity_;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordSpecMaker {

    public static Specification<WordEntity> makeSpec(WordFilter filter) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        if (!CollectionUtils.isEmpty(filter.getCategoryIds())) {
            var categoryFilter = categoryFilter(filter.getCategoryIds());
            resultSpec = resultSpec.and(categoryFilter);
        }

        if (!CollectionUtils.isEmpty(filter.getLanguages())) {
            var languageFilter = languageFilter(filter.getLanguages());
            resultSpec = resultSpec.and(languageFilter);
        }

        if (Objects.nonNull(filter.getUserId())) {
            var userFilter = userFilter(filter.getUserId());
            resultSpec = resultSpec.and(userFilter);
        }

        return resultSpec;
    }

    private static Specification<WordEntity> categoryFilter(Collection<Long> categories) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var categoriesPredicate = wordToUserEntityRoot.get(WordToUserEntity_.CATEGORY).get(CategoryEntity_.ID).in(categories);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD_ID), root.get(WordEntity_.ID));
            return builder.and(categoriesPredicate, wordPredicates);
        };
    }

    private static Specification<WordEntity> languageFilter(Collection<Language> languages) {
        return (root, query, builder) -> root.get(WordEntity_.LANGUAGE).in(languages);
    }

    private static Specification<WordEntity> userFilter(Long userId) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var userPredicate = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.USER_ID), userId);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD_ID), root.get(WordEntity_.ID));
            return builder.and(userPredicate, wordPredicates);
        };
    }
}