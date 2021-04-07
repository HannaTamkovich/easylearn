package com.easylearn.easylearn.quizz.answer.repository.converter;

import com.easylearn.easylearn.quizz.answer.model.Answer;
import com.easylearn.easylearn.quizz.answer.repository.entity.AnswerEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AnswerEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Answer toModel(@NotNull AnswerEntity answerEntity) {
        return modelMapper.map(answerEntity, Answer.class);
    }

    @NotNull
    public Collection<Answer> toModels(@NotNull Collection<AnswerEntity> wordEntities) {
        return wordEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @NotNull
    public AnswerEntity toEntity(@NotNull Answer answer) {
        return modelMapper.map(answer, AnswerEntity.class);
    }

    @NotNull
    public Collection<AnswerEntity> toEntities(@NotNull Collection<Answer> answer) {
        return answer.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
