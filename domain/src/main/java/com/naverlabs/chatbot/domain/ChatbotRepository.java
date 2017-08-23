package com.naverlabs.chatbot.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author minhyeok
 */
public interface ChatbotRepository extends CrudRepository<Chatbot, Long> {

    Iterable<Chatbot> findByEnabled(boolean enabled);
}