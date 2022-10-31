package com.a203.sixback.user.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetUserPointDTO extends BaseResponseBody {

    private Integer point;

    public static ResGetUserPointDTO of(Integer statusCode, String message, Integer point){
        ResGetUserPointDTO body = new ResGetUserPointDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setPoint(point);

        return body;
    }
}
