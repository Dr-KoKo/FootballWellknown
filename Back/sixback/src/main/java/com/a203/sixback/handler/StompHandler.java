package com.a203.sixback.handler;

import com.a203.sixback.db.enums.MessageType;
import com.a203.sixback.db.redis.ChatCacheRepository;
import com.a203.sixback.socket.BaseMessage;
import com.a203.sixback.socket.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final ChatCacheRepository chatCacheRepository;
    private final MessageService messageService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String channelId = messageService.getChannelId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidChannelId"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            log.info("Channel: {} subscribe User : {}", channelId, sessionId);

            String key = "Session_" + sessionId;

            chatCacheRepository.setChat(key, Long.parseLong(channelId));

            chatCacheRepository.plusUserCount(channelId);

            String data = chatCacheRepository.getChat(channelId);

            BaseMessage baseMessage = BaseMessage.builder().type(MessageType.INFO).sender("System").channelId(channelId).data(data).build();

            messageService.sendMessage(baseMessage);

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            String key = "Session_" + sessionId;

            String channelId = chatCacheRepository.getChat(key);

            chatCacheRepository.minusUserCount(channelId);
        }

        return message;
    }
}
