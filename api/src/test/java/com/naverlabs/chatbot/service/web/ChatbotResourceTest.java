package com.naverlabs.chatbot.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.v1.web.ChatbotResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author minhyeok
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ChatbotResourceTest {

    private JacksonTester<ChatbotResource> jacksonTester;

    private ChatbotResource resource;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        resource = jacksonTester.readObject(new ClassPathResource("chatbot_resource.json"));
    }

    @Test
    public void entityIsMutable() {
        Chatbot entity = resource.getEntity();
        assertThat(entity).isEqualTo(resource.getEntity());
    }
}
