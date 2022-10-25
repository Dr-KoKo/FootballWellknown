package com.a203.sixback.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void message(Message message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message.getData());
    }

}
