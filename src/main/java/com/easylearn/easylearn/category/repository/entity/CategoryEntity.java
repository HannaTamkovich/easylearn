package com.easylearn.easylearn.category.repository.entity;

import com.easylearn.easylearn.language.model.Language;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class CategoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6352609269422099585L;

    @Id
    @GeneratedValue(generator = "category_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
