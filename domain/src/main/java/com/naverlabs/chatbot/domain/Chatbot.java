package com.naverlabs.chatbot.domain;

import javax.persistence.*;

/**
 * @author minhyeok
 */
@Entity
public class Chatbot {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private MessengerType messengerType;

    private String messengerToken;

    private String webhookUrl;

    @Column(nullable = false)
    private boolean enabled = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public MessengerType getMessengerType() {
        return messengerType;
    }

    public void setMessengerType(MessengerType messengerType) {
        this.messengerType = messengerType;
    }

    public String getMessengerToken() {
        return messengerToken;
    }

    public void setMessengerToken(String messengerToken) {
        this.messengerToken = messengerToken;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return String.format("Chatbot[id=%s, name=%s, messengerType=%s, enabled=%b]", id, name, messengerType, enabled);
    }
}