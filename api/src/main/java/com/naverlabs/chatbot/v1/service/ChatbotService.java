package com.naverlabs.chatbot.v1.service;

import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.domain.ChatbotRepository;
import com.naverlabs.chatbot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author minhyeok
 */
@Component("chatbotService")
public class ChatbotService {

    private final ChatbotRepository chatbotRepository;

    public ChatbotService(ChatbotRepository chatbotRepository) {
        this.chatbotRepository = chatbotRepository;
    }

    public Iterable<Chatbot> findAll() {
        return chatbotRepository.findAll();
    }

    public Chatbot findOne(Long id) {
        if (!chatbotRepository.exists(id))
            throw new ResourceNotFoundException();
        return chatbotRepository.findOne(id);
    }

    public Chatbot save(Chatbot chatbot) {
        return chatbotRepository.save(chatbot);
    }

    public Chatbot update(Chatbot chatbot) {
        if (!chatbotRepository.exists(chatbot.getId()))
            throw new ResourceNotFoundException();
        return chatbotRepository.save(chatbot);
    }

    public void delete(Long id) {
        if (!chatbotRepository.exists(id))
            throw new ResourceNotFoundException();
        chatbotRepository.delete(id);
    }
}