package com.easylearn.easylearn.security.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageResponse {

    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String middleName;
    private Long dateOfLastVisit;
    private Long numberOfWords;
    private Long numberOfTests;
}
