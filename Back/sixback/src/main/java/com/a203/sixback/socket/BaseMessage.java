package com.a203.sixback.socket;

import com.a203.sixback.db.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage {
    private MessageType type;
    private String sender;
    private String channelId;
    private String data;
}