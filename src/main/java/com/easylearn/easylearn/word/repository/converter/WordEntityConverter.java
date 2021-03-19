package com.easylearn.easylearn.word.repository.converter;

import com.easylearn.easylearn.core.dto.response.PageResult;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.repository.entity.WordEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WordEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public PageResult<Word> toModel(@NotNull Page<WordEntity> wordEntities) {
        var pageResult = new PageResult<Word>();
        modelMapper.map(wordEntities, pageResult);
        pageResult.setContent(toModels(wordEntities.getContent()));
        return pageResult;
    }

    @NotNull
    public Word toModel(@NotNull WordEntity wordEntity) {
        return modelMapper.map(wordEntity, Word.class);
    }

    @NotNull
    public Collection<Word> toModels(@NotNull Collection<WordEntity> wordEntities) {
        return wordEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @NotNull
    public WordEntity toEntity(@NotNull Word word) {
        return modelMapper.map(word, WordEntity.class);
    }

    @NotNull
    public Collection<WordEntity> toEntities(@NotNull Collection<Word> word) {
        return word.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
