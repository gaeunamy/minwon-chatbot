package io.github.gaeunamy.minwon_chatbot.service;

import io.github.gaeunamy.minwon_chatbot.dto.FaqDto;
import io.github.gaeunamy.minwon_chatbot.entity.Faq;
import io.github.gaeunamy.minwon_chatbot.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    // 전체 조회
    public List<FaqDto> getAllFaqs() {
        return faqRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 등록
    public FaqDto createFaq(FaqDto faqDto) {
        Faq faq = new Faq();
        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());
        return toDto(faqRepository.save(faq));
    }

    // 수정
    public FaqDto updateFaq(Long id, FaqDto faqDto) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ를 찾을 수 없습니다."));
        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());
        return toDto(faqRepository.save(faq));
    }

    // 삭제
    public void deleteFaq(Long id) {
        faqRepository.deleteById(id);
    }

    // 키워드 검색
    public List<FaqDto> searchFaq(String keyword) {
        return faqRepository.findByQuestionContaining(keyword).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Entity -> DTO 변환
    private FaqDto toDto(Faq faq) {
        FaqDto dto = new FaqDto();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        return dto;
    }
}