package io.github.gaeunamy.minwon_chatbot.controller;

import io.github.gaeunamy.minwon_chatbot.dto.ChatDto;
import io.github.gaeunamy.minwon_chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatDto chat(@RequestBody ChatDto chatDto) {
        return chatService.chat(chatDto.getQuestion());
    }
}