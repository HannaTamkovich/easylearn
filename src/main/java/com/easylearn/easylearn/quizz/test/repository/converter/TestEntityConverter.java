package com.easylearn.easylearn.quizz.test.repository.converter;

import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.repository.entity.TestEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TestEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Test toModel(@NotNull TestEntity testEntity) {
        return modelMapper.map(testEntity, Test.class);
    }

    @NotNull
    public Collection<Test> toModels(@NotNull Collection<TestEntity> wordEntities) {
        return wordEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @NotNull
    public TestEntity toEntity(@NotNull Test test) {
        return modelMapper.map(test, TestEntity.class);
    }

    @NotNull
    public Collection<TestEntity> toEntities(@NotNull Collection<Test> test) {
        return test.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
