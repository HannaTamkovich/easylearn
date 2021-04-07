package com.easylearn.easylearn.quizz.test.repository.entity;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.quizz.question.repository.entity.QuestionEntity;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "test")
public class TestEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8780237823052118891L;

    @Id
    @GeneratedValue(generator = "test_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "test_sequence", sequenceName = "test_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "test_id")
    private Collection<QuestionEntity> questions;
}
