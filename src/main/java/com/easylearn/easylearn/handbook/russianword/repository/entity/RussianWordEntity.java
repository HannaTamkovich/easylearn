package com.easylearn.easylearn.handbook.russianword.repository.entity;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "russian_word")
@Builder
public class RussianWordEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2205076326139997883L;

    @Id
    @GeneratedValue(generator = "russian_word_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "russian_word_sequence", sequenceName = "russian_word_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String word;
}
