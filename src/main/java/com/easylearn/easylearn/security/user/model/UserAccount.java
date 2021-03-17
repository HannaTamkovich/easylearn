package com.easylearn.easylearn.security.user.model;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {

    public static final int MIN_USERNAME_SIZE = 1;
    public static final int MAX_USERNAME_SIZE = 255;

    public static final int MIN_NAME_SIZE = 1;
    public static final int MAX_NAME_SIZE = 255;

    public static final int MIN_JOB_TITLE_SIZE = 1;
    public static final int MAX_JOB_TITLE_SIZE = 255;

    public static final int MIN_ORGANIZATION_SIZE = 1;
    public static final int MAX_ORGANIZATION_SIZE = 255;

    public static final int MIN_CITY_SIZE = 1;
    public static final int MAX_CITY_SIZE = 255;

    public static final int MIN_DEPARTMENT_SIZE = 1;
    public static final int MAX_DEPARTMENT_SIZE = 255;

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
    private Role role = Role.USER;

    @NotNull
    private Instant dateOfLastVisit;

    @NotNull
    private boolean deleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
