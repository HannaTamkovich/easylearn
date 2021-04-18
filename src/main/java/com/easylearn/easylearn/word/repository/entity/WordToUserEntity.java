package com.easylearn.easylearn.word.repository.entity;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "word_to_user")
public class WordToUserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2782023319535922948L;

    @Id
    @GeneratedValue(generator = "word_to_user_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "word_to_user_sequence", sequenceName = "word_to_user_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "word_id")
    private WordEntity word;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @NotNull
    @Column(name = "number_of_answers")
    private Long numberOfAnswers = 0L;

    @NotNull
    @Column(name = "number_of_correct_answers")
    private Long numberOfCorrectAnswers = 0L;

    @NotNull
    @Column(name = "date_of_last_answer")
    private Instant dateOfLastAnswer;
}
