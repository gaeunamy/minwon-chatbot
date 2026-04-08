package io.github.gaeunamy.minwon_chatbot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqDto {

    private Long id;
    private String question;
    private String answer;
}