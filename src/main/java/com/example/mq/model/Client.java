package com.example.mq.model;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public class Client {
    private Integer userId;
    private Integer chatId;
    private SseEmitter sse;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public SseEmitter getSse() {
        return sse;
    }

    public void setSse(SseEmitter sse) {
        this.sse = sse;
    }

    public void close(){
        this.sse.complete();
    }

    public void send(Object data) throws IOException {
        SseMessage message = new SseMessage();
        message.setEvent("msg");
        message.setData(data);
        message.sendBySseEmitter(this.sse);
    }
}
