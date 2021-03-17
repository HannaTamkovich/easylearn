package com.easylearn.easylearn.word.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private Long id;
    private String word;
    private Collection<String> translations;
}
