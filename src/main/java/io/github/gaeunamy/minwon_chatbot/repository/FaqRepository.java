package io.github.gaeunamy.minwon_chatbot.repository;

import io.github.gaeunamy.minwon_chatbot.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByQuestionContaining(String keyword);

    List<Faq> findByQuestionContainingAndStatus(String keyword, Faq.FaqStatus status);
    List<Faq> findByStatus(Faq.FaqStatus status);
}