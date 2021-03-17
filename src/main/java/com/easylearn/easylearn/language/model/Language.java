package com.easylearn.easylearn.language.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

    ENGLISH("Английский язык"), SPANISH("Испанский язык"), GERMAN("Немецкий язык");

    private final String id = this.name();
    private final String description;
}
