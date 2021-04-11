package com.easylearn.easylearn.quizz.answer.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
public class AnswerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -259580524921959836L;

    @Id
    @GeneratedValue(generator = "answer_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "answer_sequence", sequenceName = "answer_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private boolean correctAnswer;
}
