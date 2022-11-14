package com.a203.sixback.user.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetUserRankDTO extends BaseResponseBody {

    private Long rank;
    private Long rankDaily;
    private Long rankWeekly;
    private Long userNum;

    public static ResGetUserRankDTO of(Integer status, String message, Long rank, Long rankDaily, Long rankWeekly, Long userNum){
        ResGetUserRankDTO body = new ResGetUserRankDTO();

        body.setStatusCode(status);
        body.setMessage(message);
        body.setRank(rank);
        body.setRankDaily(rankDaily);
        body.setRankWeekly(rankWeekly);
        body.setUserNum(userNum);

        return body;
    }
}
