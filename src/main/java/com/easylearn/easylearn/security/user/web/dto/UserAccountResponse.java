package com.easylearn.easylearn.security.user.web.dto;

import com.easylearn.easylearn.language.web.dto.LanguageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponse {

    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String middleName;
    private String email;
    private LanguageResponse language;
}
