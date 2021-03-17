package com.easylearn.easylearn.language.web.converter;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.language.web.dto.LanguageResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LanguageWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Collection<LanguageResponse> toResponses(@NotNull Collection<Language> languages) {
        return languages.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    @NotNull
    public LanguageResponse toResponse(@NotNull Language language) {
        return modelMapper.map(language, LanguageResponse.class);
    }
}
