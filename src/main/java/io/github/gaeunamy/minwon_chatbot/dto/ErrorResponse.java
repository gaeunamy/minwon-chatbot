package io.github.gaeunamy.minwon_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;      // HTTP 상태 코드
    private String message;  // 에러 설명
}