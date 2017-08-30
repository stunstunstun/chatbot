package com.naverlabs.chatbot.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.domain.ChatbotRepository;
import com.naverlabs.chatbot.exception.ResourceNotFoundException;
import com.naverlabs.chatbot.v1.service.ChatbotService;
import com.naverlabs.chatbot.v1.web.ChatbotResource;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author minhyeok
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ChatbotServiceTests {

    @MockBean
    private ChatbotRepository chatbotRepository;

    @Autowired
    private ChatbotService chatbotService;

    private JacksonTester<ChatbotResource> resourceTester;

    private JacksonTester<Chatbot> entityTester;

    private ChatbotResource resource;

    private Chatbot entity;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        resource = resourceTester.readObject(new ClassPathResource("chatbot_resource.json"));
        entity = entityTester.readObject(new ClassPathResource("chatbot_entity.json"));
    }

    @Test
    public void findAll() {
        given(chatbotRepository.findAll()).willReturn(Lists.newArrayList(entity));
        chatbotService.findAll().forEach(chatbot -> assertThat(chatbot.getName()).isNotNull());
    }

    @Test
    public void findOne() {
        given(chatbotRepository.exists(entity.getId())).willReturn(true);
        given(chatbotRepository.findOne(entity.getId())).willReturn(entity);
        assertThat(chatbotService.findOne(entity.getId())).isNotNull();
    }

    @Test
    public void save() {
        given(chatbotRepository.save(resource.getEntity())).willReturn(entity);
        chatbotService.save(resource);
    }

    @Test
    public void update() throws IOException {
        Chatbot entity = resource.getEntity();
        entity.setId(entity.getId());

        given(chatbotRepository.exists(entity.getId())).willReturn(true);
        given(chatbotRepository.save(entity)).willReturn(entity);

        Chatbot updated = chatbotService.update(entity.getId(), resource);
        assertThat(updated).isNotNull();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneAndResourceNotFoundException() throws IOException {
        given(chatbotRepository.exists(entity.getId())).willReturn(false);
        chatbotService.findOne(entity.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateAndResourceNotFoundException() throws IOException {
        given(chatbotRepository.exists(entity.getId())).willReturn(false);

        Chatbot updated = chatbotService.update(entity.getId(), resource);
        assertThat(updated.isEnabled()).isFalse();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAndResourceNotFoundException() throws IOException {
        given(chatbotRepository.exists(entity.getId())).willReturn(false);

        chatbotService.delete(entity.getId());
    }
}
