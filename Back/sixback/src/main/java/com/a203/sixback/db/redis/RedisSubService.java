package com.a203.sixback.db.redis;

import com.a203.sixback.socket.ChatMessage;
import com.a203.sixback.socket.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisSubService implements MessageListener {
    private final ObjectMapper mapper = new ObjectMapper();

    private final MessageService messageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);
            messageService.sendMessage(chatMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}