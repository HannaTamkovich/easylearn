package com.easylearn.easylearn.word.web.converter;

import com.easylearn.easylearn.word.model.Card;
import com.easylearn.easylearn.word.web.dto.CardResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CardWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Collection<CardResponse> toResponses(@NotNull Collection<Card> cards) {
        return cards.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    @NotNull
    public CardResponse toResponse(@NotNull Card card) {
        return modelMapper.map(card, CardResponse.class);
    }
}
