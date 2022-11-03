package com.a203.sixback.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/chat              - 메시지 발행
    */

    @MessageMapping("/chat")
    public void chat(ChatMessage message) {
        messageService.sendMessage(message);
    }
}