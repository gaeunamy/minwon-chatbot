package io.github.gaeunamy.minwon_chatbot.service;

import io.github.gaeunamy.minwon_chatbot.dto.ChatDto;
import io.github.gaeunamy.minwon_chatbot.entity.ChatLog;
import io.github.gaeunamy.minwon_chatbot.repository.ChatLogRepository;
import io.github.gaeunamy.minwon_chatbot.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final FaqRepository faqRepository;
    private final ChatLogRepository chatLogRepository;
    private final OpenAiChatClient openAiChatClient;

    public ChatDto chat(String question) {

        // 1. FAQ 검색
        var faqs = faqRepository.findByQuestionContaining(question);

        String answer;

        if (!faqs.isEmpty()) {
            // 2. FAQ에 있으면 FAQ 답변 반환
            answer = faqs.get(0).getAnswer();
        } else {
            // 3. FAQ에 없으면 OpenAI 호출
            answer = openAiChatClient.call(
                    "당신은 공공기관 민원 안내 챗봇입니다. 다음 질문에 친절하게 답변해주세요.\n질문: " + question
            );
        }

        // 4. 대화 로그 저장
        ChatLog chatLog = new ChatLog();
        chatLog.setQuestion(question);
        chatLog.setAnswer(answer);
        chatLogRepository.save(chatLog);

        // 5. 결과 반환
        ChatDto chatDto = new ChatDto();
        chatDto.setQuestion(question);
        chatDto.setAnswer(answer);
        return chatDto;
    }
}