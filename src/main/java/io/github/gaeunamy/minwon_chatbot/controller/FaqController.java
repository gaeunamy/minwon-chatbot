package io.github.gaeunamy.minwon_chatbot.controller;

import io.github.gaeunamy.minwon_chatbot.dto.FaqDto;
import io.github.gaeunamy.minwon_chatbot.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    // 전체 조회
    @GetMapping
    public List<FaqDto> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    // 등록
    @PostMapping
    public FaqDto createFaq(@RequestBody FaqDto faqDto) {
        return faqService.createFaq(faqDto);
    }

    // 수정
    @PutMapping("/{id}")
    public FaqDto updateFaq(@PathVariable Long id, @RequestBody FaqDto faqDto) {
        return faqService.updateFaq(id, faqDto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
    }

    // PENDING 목록 조회
    @GetMapping("/pending")
    public List<FaqDto> getPendingFaqs() {
        return faqService.getPendingFaqs();
    }

    // 승인
    @PutMapping("/{id}/approve")
    public FaqDto approveFaq(@PathVariable Long id) {
        return faqService.approveFaq(id);
    }

    // 거절
    @DeleteMapping("/{id}/reject")
    public void rejectFaq(@PathVariable Long id) {
        faqService.rejectFaq(id);
    }
}