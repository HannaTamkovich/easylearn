package com.easylearn.easylearn.security.useractivation.repository.entity;

import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_activation")
public class UserActivationEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3567289308535828487L;

    @Id
    @GeneratedValue(generator = "user_activation_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_activation_sequence", sequenceName = "user_activation_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Column(name = "activation_code")
    private String activationCode;

    @NotNull
    @Column(name = "invalidate_date")
    private Instant invalidateDate;
}
