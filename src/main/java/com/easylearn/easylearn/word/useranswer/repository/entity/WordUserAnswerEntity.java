package com.easylearn.easylearn.word.useranswer.repository.entity;

import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "word_user_answer")
public class WordUserAnswerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7519930011800535622L;

    @Id
    @GeneratedValue(generator = "word_user_answer_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "word_user_answer_sequence", sequenceName = "word_user_answer_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    private Long allAnswers = 0L;

    @NotNull
    private Long correctAnswers = 0L;
}
