package io.github.gaeunamy.minwon_chatbot.service;

import io.github.gaeunamy.minwon_chatbot.dto.ChatDto;
import io.github.gaeunamy.minwon_chatbot.entity.ChatLog;
import io.github.gaeunamy.minwon_chatbot.entity.Faq;
import io.github.gaeunamy.minwon_chatbot.repository.ChatLogRepository;
import io.github.gaeunamy.minwon_chatbot.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final FaqRepository faqRepository;
    private final ChatLogRepository chatLogRepository;
    private final OpenAiChatClient openAiChatClient;

    public ChatDto chat(String question) {

        // 1. 키워드 추출 (2글자 이상 단어만)
        kr.co.shineware.nlp.komoran.core.Komoran komoran =
                new kr.co.shineware.nlp.komoran.core.Komoran(
                        kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL.LIGHT
                );

        List<String> keywords = komoran.analyze(question)
                .getTokenList()
                .stream()
                .filter(token -> token.getPos().startsWith("N") || token.getPos().startsWith("V"))
                .map(kr.co.shineware.nlp.komoran.model.Token::getMorph)
                .filter(word -> word.length() >= 2)
                .distinct()
                .toList();

        // 2. 키워드로 FAQ 검색
        List<Faq> faqs = keywords.stream()
                .flatMap(keyword -> faqRepository.findByQuestionContaining(keyword).stream())
                .distinct()
                .toList();

        String answer;

        if (!faqs.isEmpty()) {
            // 3. FAQ 있으면 첫 번째 답변 반환
            answer = faqs.get(0).getAnswer();
        } else {
            // 4. FAQ 없으면 OpenAI 호출
            answer = openAiChatClient.call(
                    "당신은 대한민국 공공기관 민원 안내 챗봇입니다.\n" +
                            "다음 규칙을 반드시 따르세요.\n" +
                            "1. 답변은 3문장 이내로 작성하세요.\n" +
                            "2. 존댓말을 사용하세요.\n" +
                            "3. 민원 처리 절차와 필요 서류를 중심으로 안내하세요.\n" +
                            "4. 모르는 내용은 '해당 기관에 문의하세요'라고 답하세요.\n" +
                            "질문: " + question
            );

            // 5. OpenAI 답변을 FAQ DB에 자동 저장
            Faq newFaq = new Faq();
            newFaq.setQuestion(question);
            newFaq.setAnswer(answer);
            faqRepository.save(newFaq);
        }

        // 6. 대화 로그 저장
        ChatLog chatLog = new ChatLog();
        chatLog.setQuestion(question);
        chatLog.setAnswer(answer);
        chatLogRepository.save(chatLog);

        // 7. 결과 반환
        ChatDto chatDto = new ChatDto();
        chatDto.setQuestion(question);
        chatDto.setAnswer(answer);
        return chatDto;
    }
}