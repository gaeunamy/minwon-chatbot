package io.github.gaeunamy.minwon_chatbot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatDto {

    private String question;
    private String answer;
}