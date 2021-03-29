package com.easylearn.easylearn.security.user.web.converter;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.web.dto.UserPageResponse;
import com.easylearn.easylearn.security.user.web.dto.UserResponse;
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Validated
@Component
@AllArgsConstructor
public class UserWebConverter {

    private final ModelMapper modelMapper;

    private final WordToUserRepository wordToUserRepository;

    @NotNull
    public UserResponse toResponse(@NotNull User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    @NotNull
    public Collection<UserPageResponse> toUsersPageResponses(@NotNull Collection<User> users) {
        return users.stream().map(this::toPageResponse).collect(toSet());
    }

    private UserPageResponse toPageResponse(User user) {
        var userResponse = modelMapper.map(user, UserPageResponse.class);
        userResponse.setNumberOfWords(wordToUserRepository.countByUserId(user.getId()));
        userResponse.setNumberOfTests(0L);
        return userResponse;
    }
}
