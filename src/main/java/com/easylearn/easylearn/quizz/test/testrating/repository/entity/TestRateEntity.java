package com.easylearn.easylearn.quizz.test.testrating.repository.entity;

import com.easylearn.easylearn.quizz.test.repository.entity.TestEntity;
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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "test_rating")
public class TestRateEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7133949398862749091L;

    @Id
    @GeneratedValue(generator = "test_rating_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "test_rating_sequence", sequenceName = "test_rating_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @NotNull
    private Integer rate;
}
