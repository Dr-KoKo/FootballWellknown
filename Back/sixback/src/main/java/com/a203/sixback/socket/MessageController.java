package com.a203.sixback.socket;

import com.a203.sixback.db.redis.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final RedisPubService redisPubService;

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/chat              - 메시지 발행
    */

    @MessageMapping("/chat")
    public void chat(ChatMessage message) {
        redisPubService.sendMessage(message);
    }
}