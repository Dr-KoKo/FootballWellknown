package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.util.model.BaseResponseBody;

import java.util.List;
import java.util.stream.Collectors;


public class ResGetUserPredictsDTO extends BaseResponseBody {

    List<UserPredictDTO> userPredictDTOList;

    public static ResGetUserPredictsDTO of(Integer statusCode, String message, List<Predict> predictList){
        ResGetUserPredictsDTO body = new ResGetUserPredictsDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.userPredictDTOList = predictList.stream().map(UserPredictDTO::new).collect(Collectors.toList());

        return body;
    }


    private static class UserPredictDTO {
        private String home;
        private String homeImg;
        private String away;
        private String awayImg;
        private MatchResult myPredict;

        private UserPredictDTO(Predict predict){
            this.home = predict.getMatch().getHome().getName();
            this.homeImg = predict.getMatch().getHome().getImage();
            this.away = predict.getMatch().getAway().getName();
            this.awayImg = predict.getMatch().getAway().getImage();
            this.myPredict = predict.getMatchResult();
        }
    }
}
