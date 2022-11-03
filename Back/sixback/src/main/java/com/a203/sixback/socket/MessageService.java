package com.a203.sixback.socket;

import com.a203.sixback.db.enums.MessageType;
import com.a203.sixback.db.redis.ChatCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatCacheRepository chatCacheRepository;

    public void sendMessage(BaseMessage message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }

    public void sendMessage(ChatMessage message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }

    public String getChannelId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) return destination.substring(lastIndex + 1);
        else return "";
    }

    public void getUserCount(String channelId) {
        String data = chatCacheRepository.getChat(channelId);
        BaseMessage message = BaseMessage.builder().type(MessageType.INFO).sender("System").channelId(channelId).data(data).build();
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + channelId, message);
    }

}
