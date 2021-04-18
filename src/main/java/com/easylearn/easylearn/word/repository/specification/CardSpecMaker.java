package com.easylearn.easylearn.word.repository.specification;

import com.easylearn.easylearn.security.user.repository.entity.UserEntity_;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import com.easylearn.easylearn.word.repository.entity.WordEntity_;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CardSpecMaker {

    public static Specification<WordEntity> makeSpec(String username) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        if (StringUtils.isNotBlank(username)) {
            var userFilter = userFilter(username);
            resultSpec = resultSpec.and(userFilter);
        }

        return resultSpec;
    }

    private static Specification<WordEntity> userFilter(String username) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var userPredicate = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.USER).get(UserEntity_.USERNAME), username);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD).get(WordEntity_.ID), root.get(WordEntity_.ID));
            return builder.and(userPredicate, wordPredicates);
        };
    }
}