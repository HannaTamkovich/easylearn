package com.easylearn.easylearn.word.repository.specification;

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
public final class CardSpecMaker {

    public static Specification<WordEntity> makeSpec(Long userId) {
        var resultSpec = (Specification<WordEntity>) (root, query, builder) -> root.get(WordEntity_.ID).isNotNull();

        if (Objects.nonNull(userId)) {
            var userFilter = userFilter(userId);
            resultSpec = resultSpec.and(userFilter);
        }

        return resultSpec;
    }

    private static Specification<WordEntity> userFilter(Long userId) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<WordToUserEntity> wordToUserEntityRoot = query.from(WordToUserEntity.class);
            var userPredicate = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.USER_ID), userId);
            var wordPredicates = builder.equal(wordToUserEntityRoot.get(WordToUserEntity_.WORD_ID), root.get(WordEntity_.ID));
            //query.orderBy(builder.desc(wordToUserEntityRoot.get(WordToUserEntity_.DATE_OF_LAST_ANSWER)));
            return builder.and(userPredicate, wordPredicates);
        };
    }
}