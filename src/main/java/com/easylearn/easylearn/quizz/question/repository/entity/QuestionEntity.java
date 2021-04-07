package com.easylearn.easylearn.quizz.question.repository.entity;

import com.easylearn.easylearn.quizz.answer.repository.entity.AnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class QuestionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4453774449207567694L;

    @Id
    @GeneratedValue(generator = "question_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "question_sequence", sequenceName = "question_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String text;

    @NotNull
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private Collection<AnswerEntity> answers;
}
