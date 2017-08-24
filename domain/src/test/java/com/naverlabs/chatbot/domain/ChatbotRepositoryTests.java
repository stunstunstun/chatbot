package com.naverlabs.chatbot.domain;

import com.naverlabs.chatbot.AbstractDataJpaTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author minhyeok
 */
public class ChatbotRepositoryTests extends AbstractDataJpaTest {

    @Autowired
    private ChatbotRepository chatbotRepository;

    @Test
    public void findAll() {
        Iterable<Chatbot> bots = chatbotRepository.findAll();
        assertThat(Lists.newArrayList(bots).size()).isEqualTo(5);
    }

    @Test
    public void findOne() {
        Chatbot entity = chatbotRepository.findOne(1L);
        assertThat(entity).isNotNull();
    }

    @Test
    public void findByEnabled() {
        Iterable<Chatbot> enabledBots = chatbotRepository.findByEnabled(true);
        Iterable<Chatbot> disabledBots = chatbotRepository.findByEnabled(true);

        assertThat(enabledBots).isNotEmpty();
        assertThat(disabledBots).isNotEmpty();
        enabledBots.forEach(c -> assertThat(c.getName()).isNotNull());
        enabledBots.forEach(c -> assertThat(c.getName()).isNotNull());
    }

    @Test
    public void save() {
        Chatbot entity = new Chatbot();
        entity.setName("Travel Bot");
        entity.setMessengerType(MessengerType.LINE);
        entity.setMessengerToken("EAAR4t31aREoBACDzWk5luFqjfJZB4wIH4WdbS4p64KZBf7iPfex44TnXTRFws4LFAwilkkytW9K1v1ADOgZAB6hbtnPMVmAUfmRNGfmFBRFLz5ABzi8ZBPtonrAhFo7yxzDoIdNs1QwnXrvlDkgm8IXLTbjbsKfMUFW0Uq6yvgZDZD");
        entity.setWebhookUrl("http://localhost/webhook");
        entity.setEnabled(false);

        entity = chatbotRepository.save(entity);
        assertThat(chatbotRepository.exists(entity.getId())).isTrue();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithNullValue() {
        Chatbot entity = new Chatbot();
        entity.setEnabled(false);

        entity = chatbotRepository.save(entity);
        assertThat(chatbotRepository.exists(entity.getId())).isTrue();
    }

    @Test
    public void update() {
        Chatbot entity = chatbotRepository.findOne(2L);
        entity.setEnabled(false);
        chatbotRepository.save(entity);
        Assertions.assertThat(chatbotRepository.findOne(2L).isEnabled()).isFalse();
    }

    @Test
    public void delete() {
        chatbotRepository.delete(1L);
        assertThat(chatbotRepository.exists(1L)).isFalse();
    }
}
