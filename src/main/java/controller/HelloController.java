package io.github.gaeunamy.minwon_chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "안녕하세요! 가은님의 민원 AI 챗봇 서버가 정상 작동 중입니다! 🎉";
    }
}