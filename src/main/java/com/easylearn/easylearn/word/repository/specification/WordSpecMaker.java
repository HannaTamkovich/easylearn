package com.easylearn.easylearn.word.repository.specification;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity_;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity_;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import com.easylearn.easylearn.word.repository.entity.WordEntity_;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity_;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordSpecMaker {

    public static Specification<WordEntity> makeSpec(WordFilter filter) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        if (Objects.nonNull(filter.getCategoryId())) {
            var categoryFilter = categoryFilter(filter.getCategoryId());
            resultSpec = resultSpec.and(categoryFilter);
        }

        if (StringUtils.isNotBlank(filter.getUsername())) {
            var userFilter = userFilter(filter.getUsername());
            resultSpec = resultSpec.and(userFilter);
        }

        return resultSpec;
    }

    public static Specification<WordEntity> makeSpecForFreeWords(@NotNull Long userId) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        var wordSpecification = wordFilter(userId);
        resultSpec = resultSpec.and(wordSpecification);

        return resultSpec;
    }

    private static Specification<WordEntity> wordFilter(@NotNull Long userId) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD).get(WordEntity_.ID), root.get(WordEntity_.ID));
            var userPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.USER).get(UserEntity_.ID), userId);
            var categoriesPredicate = wordToUserEntityRoot.get(WordToUserEntity_.CATEGORY).isNull();
            return builder.and(categoriesPredicate, wordPredicates, userPredicates);
        };
    }

    private static Specification<WordEntity> categoryFilter(Long category) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var categoriesPredicate = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.CATEGORY).get(CategoryEntity_.ID), category);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD).get(WordEntity_.ID), root.get(WordEntity_.ID));
            return builder.and(categoriesPredicate, wordPredicates);
        };
    }

    private static Specification<WordEntity> userFilter(String username) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            Root<UserEntity> userEntityRoot = query.from(UserEntity.class);
            var userPredicate = builder.and(
                    builder.equal(userEntityRoot.get(UserEntity_.USERNAME), username),
                    builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.USER).get(UserEntity_.ID), userEntityRoot.get(UserEntity_.ID))
            );
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD).get(WordEntity_.ID), root.get(WordEntity_.ID));
            return builder.and(userPredicate, wordPredicates);
        };
    }
}