package com.easylearn.easylearn.quizz.question.repository.converter;

import com.easylearn.easylearn.quizz.question.model.Question;
import com.easylearn.easylearn.quizz.question.repository.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class QuestionEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Question toModel(@NotNull QuestionEntity questionEntity) {
        return modelMapper.map(questionEntity, Question.class);
    }

    @NotNull
    public Collection<Question> toModels(@NotNull Collection<QuestionEntity> wordEntities) {
        return wordEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @NotNull
    public QuestionEntity toEntity(@NotNull Question question) {
        return modelMapper.map(question, QuestionEntity.class);
    }

    @NotNull
    public Collection<QuestionEntity> toEntities(@NotNull Collection<Question> question) {
        return question.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
