package com.naverlabs.chatbot.v1.service;

import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.domain.ChatbotRepository;
import com.naverlabs.chatbot.exception.ResourceNotFoundException;
import com.naverlabs.chatbot.v1.web.ChatbotResource;
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

    public Chatbot save(ChatbotResource resource) {
        return chatbotRepository.save(resource.getEntity());
    }

    public Chatbot update(Long id, ChatbotResource resource) {
        if (!chatbotRepository.exists(id))
            throw new ResourceNotFoundException();
        Chatbot entity = resource.getEntity();
        entity.setId(id);
        return chatbotRepository.save(entity);
    }

    public void delete(Long id) {
        if (!chatbotRepository.exists(id))
            throw new ResourceNotFoundException();
        chatbotRepository.delete(id);
    }
}