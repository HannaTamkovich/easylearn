package com.easylearn.easylearn.word.repository.entity;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "word")
public class WordEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3635307576811445561L;

    @Id
    @GeneratedValue(generator = "word_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "word_sequence", sequenceName = "word_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String word;

    @NotNull
    private String translation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;
}
