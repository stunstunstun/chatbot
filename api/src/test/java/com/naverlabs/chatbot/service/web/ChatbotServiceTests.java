package com.naverlabs.chatbot.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naverlabs.chatbot.EndPoints;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.domain.ChatbotRepository;
import com.naverlabs.chatbot.domain.MessengerType;
import com.naverlabs.chatbot.exception.ResourceNotFoundException;
import com.naverlabs.chatbot.v1.service.ChatbotService;
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

    private JacksonTester<Chatbot> resource;

    private Chatbot entity;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        entity = resource.readObject(new ClassPathResource("chatbot_resource.json"));
    }

    @Test
    public void findAll() {
        given(chatbotRepository.findAll()).willReturn(Lists.newArrayList(entity));
        chatbotService.findAll().forEach(chatbot -> assertThat(chatbot.getName()).isNotNull());
    }

    @Test
    public void findOne() {
        given(chatbotRepository.findOne(entity.getId())).willReturn(entity);
        assertThat(chatbotService.findOne(entity.getId())).isNotNull();
    }

    @Test
    public void update() throws IOException {
        given(chatbotRepository.exists(entity.getId())).willReturn(true);

        entity.setEnabled(false);
        entity.setMessengerType(MessengerType.LINE);
        given(chatbotRepository.save(entity)).willReturn(entity);

        Chatbot updated = chatbotService.update(entity);
        assertThat(updated.isEnabled()).isFalse();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneAndResourceNotFoundException() throws IOException {
        Chatbot chatbot = resource.readObject(new ClassPathResource("chatbot_resource.json"));
        given(chatbotRepository.exists(chatbot.getId())).willReturn(false);

        chatbotService.findOne(chatbot.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateAndResourceNotFoundException() throws IOException {
        Chatbot chatbot = resource.readObject(new ClassPathResource("chatbot_resource.json"));
        given(chatbotRepository.exists(chatbot.getId())).willReturn(false);

        Chatbot updated = chatbotService.update(chatbot);
        assertThat(updated.isEnabled()).isFalse();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAndResourceNotFoundException() throws IOException {
        Chatbot chatbot = resource.readObject(new ClassPathResource("chatbot_resource.json"));
        given(chatbotRepository.exists(chatbot.getId())).willReturn(false);

        chatbotService.delete(chatbot.getId());
    }
}
