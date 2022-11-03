package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.User;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetUserDetailsDTO extends BaseResponseBody {
    private String email;
    private String nickname;
    private Integer point;

    public static ResGetUserDetailsDTO of(Integer statusCode, String message, User user) {
        ResGetUserDetailsDTO body = new ResGetUserDetailsDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.email = user.getEmail();
        body.nickname = user.getNickname();
        body.point = user.getPoint();
        return body;
    }
}
