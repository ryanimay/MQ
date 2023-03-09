package com.example.mq.service;

import com.example.mq.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class ChatService {
    private static Map<Integer, List<Client>> clientMap = new ConcurrentHashMap<>();

    public void connect(Integer chatId){
        Client client = new Client();
        client.setChatId(chatId);
        if(clientMap.containsKey(chatId)){
            System.out.println("連線chat:" + chatId);
            client.setSse(clientMap.get(chatId).get(0).getSse());
            clientMap.get(chatId).add(client);
        }else{
            System.out.println("新建chat:" + chatId);
            client.setSse(new SseEmitter(0L));
            List<Client> list = new ArrayList<>();
            list.add(client);
            clientMap.put(chatId, list);
        }
        client.getSse().onCompletion(() -> {
            this.closeClient(client);
        });
    }

    public void closeClient(Client client) {
        Integer chatId = client.getChatId();
        if (!clientMap.containsKey(chatId)) {
            return;
        }
        List<Client> chatClients = clientMap.get(chatId);
        for (Client chatClient : chatClients) {
            if (chatClient.getUserId().equals(client.getUserId())) {
                chatClients.remove(chatClient);
                return;
            }
        }
    }
}
