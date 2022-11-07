package com.a203.sixback.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseMessage{
    private String predict;

    @Override
    public String toString() {
        return getType().toString() + " : " + getSender() + " : " + getChannelId() + " : " + getPredict() + " : " + getData();
    }
}
