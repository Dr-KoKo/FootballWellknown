package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.enums.MatchStatus;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResGetUserPredictsDTO extends BaseResponseBody {

    List<UserPredictDTO> userPredictList;

    public static ResGetUserPredictsDTO of(Integer statusCode, String message, List<Predict> predictList){
        ResGetUserPredictsDTO body = new ResGetUserPredictsDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.userPredictList = predictList.stream().map(UserPredictDTO::new).collect(Collectors.toList());

        return body;
    }

    @Getter
    private static class UserPredictDTO {
        private int round;
        private String home;
        private String homeImg;
        private String away;
        private String awayImg;
        private MatchResult myPredict;
        private MatchResult matchResult;

        private UserPredictDTO(Predict predict){
            this.round = predict.getMatches().getRound();
            this.home = predict.getMatches().getHome().getName();
            this.homeImg = predict.getMatches().getHome().getImage();
            this.away = predict.getMatches().getAway().getName();
            this.awayImg = predict.getMatches().getAway().getImage();
            this.myPredict = predict.getMatchResult();
            if(predict.getMatches().getMatchStatus() == MatchStatus.FIN){
                if(predict.getMatches().getHomeScore() > predict.getMatches().getAwayScore()){
                    this.matchResult = MatchResult.HOME;
                }
                else if(predict.getMatches().getHomeScore() < predict.getMatches().getAwayScore()){
                    this.matchResult = MatchResult.AWAY;
                }
                else{
                    this.matchResult = MatchResult.DRAW;
                }
            }
        }
    }
}
