package com.easylearn.easylearn.handbook.russianword.repository;

import com.easylearn.easylearn.handbook.russianword.repository.entity.RussianWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RussianWordRepository extends JpaRepository<RussianWordEntity, Long> {

    @Query(value = "SELECT * FROM RUSSIAN_WORD r WHERE r.WORD NOT LIKE ?1 ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    RussianWordEntity findRussianWord(String translation);

    boolean existsByWord(String word);
}
