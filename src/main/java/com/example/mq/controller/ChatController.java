package com.example.mq.controller;

import com.example.mq.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/connect")
    public String connect(Integer chatId){
        System.out.println("連接:" + chatId);
        chatService.connect(chatId);
        return "OK";
    }

    @GetMapping("/send")
    public String send(Integer chatId, String message){
        chatService.send(chatId, message);
        return "OK";
    }
}
