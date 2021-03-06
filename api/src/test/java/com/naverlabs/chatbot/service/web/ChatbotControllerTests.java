package com.naverlabs.chatbot.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naverlabs.chatbot.EndPoints;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.v1.service.ChatbotService;
import com.naverlabs.chatbot.v1.web.ChatbotController;
import com.naverlabs.chatbot.v1.web.ChatbotResource;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author minhyeok
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ChatbotController.class)
public class ChatbotControllerTests {

    @MockBean
    private ChatbotService chatbotService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<ChatbotResource> resourceTester;

    private JacksonTester<Chatbot> entityTester;

    private Iterable<Chatbot> resources;

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
    public void list() throws Exception {
        given(chatbotService.findAll()).willReturn(Lists.newArrayList(entity));

        mvc.perform(get(EndPoints.BOTS)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(entity.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(entity.getName())))
                .andExpect(jsonPath("$[0].messengerType", is(entity.getMessengerType().name())))
                .andExpect(jsonPath("$[0].messengerToken", is(entity.getMessengerToken())))
                .andExpect(jsonPath("$[0].webhookUrl", is(entity.getWebhookUrl())))
                .andExpect(jsonPath("$[0].enabled", is(entity.isEnabled())));
    }

    @Test
    public void listAndresourceNotFound() throws Exception {
        mvc.perform(get("/chatbot/undefined")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findOne() throws Exception {
        given(chatbotService.findOne(entity.getId())).willReturn(entity);

        mvc.perform(get(EndPoints.BOTS + "/{id}", entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId().intValue())))
                .andExpect(jsonPath("$.name", is(entity.getName())))
                .andExpect(jsonPath("$.messengerType", is(entity.getMessengerType().name())))
                .andExpect(jsonPath("$.messengerToken", is(entity.getMessengerToken())))
                .andExpect(jsonPath("$.webhookUrl", is(entity.getWebhookUrl())))
                .andExpect(jsonPath("$.enabled", is(entity.isEnabled())));
    }

    @Test
    public void findOneAndArgumentTypeMismatched() throws Exception {
        mvc.perform(get(EndPoints.BOTS + "/{id}", "naverlabs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void save() throws Exception {
        given(chatbotService.save(resource)).willReturn(entity);

        mvc.perform(post(EndPoints.BOTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resourceTester.write(resource).getJson()))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveAndUnsupportedMediaType() throws Exception {
        given(chatbotService.save(resource)).willReturn(entity);

        mvc.perform(post(EndPoints.BOTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_XML)
                .content("<chatbot></chatbot>"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void saveAndRequestBodyNotReadable() throws Exception {
        given(chatbotService.save(resource)).willReturn(entity);

        mvc.perform(post(EndPoints.BOTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveAndRequestBodyIsNotValid() throws Exception {
        resource.setName("");
        given(chatbotService.save(resource)).willReturn(entity);

        mvc.perform(post(EndPoints.BOTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resourceTester.write(resource).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        given(chatbotService.update(entity.getId(), resource)).willReturn(entity);

        mvc.perform(put(EndPoints.BOTS + "/{id}", entity.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resourceTester.write(resource).getJson()))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateAndUnsupportedMediaType() throws Exception {
        given(chatbotService.update(entity.getId(), resource)).willReturn(entity);

        mvc.perform(put(EndPoints.BOTS + "/{id}", entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void updateAndRequestBodyNotReadable() throws Exception {
        given(chatbotService.update(entity.getId(), resource)).willReturn(entity);

        mvc.perform(put(EndPoints.BOTS + "/{id}", entity.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAndRequestBodyIsNotValid() throws Exception {
        resource.setName("");
        given(chatbotService.update(entity.getId(), resource)).willReturn(entity);

        mvc.perform(put(EndPoints.BOTS + "/{id}", entity.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resourceTester.write(resource).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteOne() throws Exception {
        mvc.perform(delete(EndPoints.BOTS + "/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }
}
