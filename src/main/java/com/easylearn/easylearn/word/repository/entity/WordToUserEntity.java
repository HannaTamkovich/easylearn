package com.easylearn.easylearn.word.repository.entity;

import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Long userId;

    @NotNull
    private Long wordId;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @NotNull
    private Long numberOfAnswers = 0L;

    @NotNull
    private Long numberOfCorrectAnswers = 0L;

    @NotNull
    private Instant dateOfLastAnswer;
}
