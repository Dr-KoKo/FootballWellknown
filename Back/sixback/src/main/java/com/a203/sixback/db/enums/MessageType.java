package com.a203.sixback.db.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum MessageType {
    MESSAGE("MESSAGE", "메시지"),
    INFO("INFO", "정보"),
    NOTICE("NOTICE", "공지사항");

    private final String code;
    private final String displayName;

    public static MessageType of(String code) {
        return Arrays.stream(MessageType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny().get();
    }
}
