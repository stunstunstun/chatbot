package com.naverlabs.chatbot.v1.web;

import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.domain.MessengerType;
import com.naverlabs.chatbot.v1.Resource;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author minhyeok
 */
public class ChatbotResource implements Resource<Chatbot> {

    @NotEmpty
    private String name;

    @NotNull
    private MessengerType messengerType;

    private String messengerToken;

    private String webhookUrl;

    private boolean enabled;

    private Chatbot entity;

    @Override
    public Chatbot getEntity() {
        if (entity == null)
            entity = new Chatbot();
        entity.setName(this.name);
        entity.setMessengerType(this.messengerType);
        entity.setMessengerToken(this.messengerToken);
        entity.setWebhookUrl(this.webhookUrl);
        entity.setEnabled(this.enabled);
        return entity;
    }

    public String getName() {
        return name;
    }

    public MessengerType getMessengerType() {
        return messengerType;
    }

    public String getMessengerToken() {
        return messengerToken;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessengerType(MessengerType messengerType) {
        this.messengerType = messengerType;
    }

    public void setMessengerToken(String messengerToken) {
        this.messengerToken = messengerToken;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
