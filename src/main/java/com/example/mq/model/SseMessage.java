package com.example.mq.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.Serializable;

public class SseMessage implements Serializable {
    private String event;
    private Object data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void sendBySseEmitter(SseEmitter sseEmitter) throws IOException {
        String messageBody = "";
        if (this.data instanceof String) {
            messageBody = this.data.toString();
        } else {
            messageBody = new ObjectMapper().writeValueAsString(this.data);
        }
        sseEmitter.send(
                SseEmitter.event()
                        .name(this.event)
                        .id(System.currentTimeMillis() + "")
                        .data(messageBody)
        );
    }
}
