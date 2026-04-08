package io.github.gaeunamy.minwon_chatbot.repository;

import io.github.gaeunamy.minwon_chatbot.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}