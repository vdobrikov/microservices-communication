package com.vdobrikov.webservice.web.rest;

import java.util.List;

public class HelloResponse {
    private List<String> messages;

    public HelloResponse() {
    }

    public HelloResponse(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
