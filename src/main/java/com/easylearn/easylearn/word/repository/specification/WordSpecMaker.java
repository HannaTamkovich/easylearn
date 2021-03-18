package com.easylearn.easylearn.word.repository.specification;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity_;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import com.easylearn.easylearn.word.repository.entity.WordEntity_;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordSpecMaker {

    public static Specification<WordEntity> makeSpec(WordFilter filter) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        if (!Objects.nonNull(filter.getCategoryId())) {
            var categoryFilter = categoryFilter(filter.getCategoryId());
            resultSpec = resultSpec.and(categoryFilter);
        }

        if (Objects.nonNull(filter.getUserId())) {
            var userFilter = userFilter(filter.getUserId());
            resultSpec = resultSpec.and(userFilter);
        }

        return resultSpec;
    }

    private static Specification<WordEntity> categoryFilter(Long category) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var categoriesPredicate = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.CATEGORY).get(CategoryEntity_.ID), category);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD_ID), root.get(WordEntity_.ID));
            return builder.and(categoriesPredicate, wordPredicates);
        };
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