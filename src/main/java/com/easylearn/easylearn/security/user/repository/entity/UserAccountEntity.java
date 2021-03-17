package com.easylearn.easylearn.security.user.repository.entity;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import static com.easylearn.easylearn.security.user.model.UserAccount.MAX_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MAX_USERNAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MIN_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MIN_USERNAME_SIZE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserAccountEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1965425860331001828L;

    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Size(min = MIN_USERNAME_SIZE, max = MAX_USERNAME_SIZE)
    private String username;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String firstName;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String secondName;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String middleName;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Column(name = "pass")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    private Instant dateOfLastVisit;

    @NotNull
    private boolean deleted;
}
