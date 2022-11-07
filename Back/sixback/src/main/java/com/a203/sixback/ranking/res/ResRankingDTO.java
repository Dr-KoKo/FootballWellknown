package com.a203.sixback.ranking.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResRankingDTO extends BaseResponseBody {

    private List<ResponseRankingDTO> responseRankingDTOList;

    public static ResRankingDTO of(Integer statusCode, String message,List<ResponseRankingDTO> responseRankingDTOList){
        ResRankingDTO body = new ResRankingDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setResponseRankingDTOList(responseRankingDTOList);

        return body;
    }
}
