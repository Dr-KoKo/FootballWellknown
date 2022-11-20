package com.a203.sixback.ranking.res;

import lombok.Getter;
import org.springframework.data.redis.core.ZSetOperations;

@Getter
public class ResponseRankingDTO {
    private String value;
    private Double score;

    public static ResponseRankingDTO convertToResponseRankingDto(ZSetOperations.TypedTuple<String> typedTuple){
        ResponseRankingDTO body = new ResponseRankingDTO();

        body.value = typedTuple.getValue();
        body.score = typedTuple.getScore();

        return body;
    }
}
