package com.a203.sixback.user.res;

import com.a203.sixback.db.entity.PointLog;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResGetUserPointDTO extends BaseResponseBody {

    private List<GetPointDTO> pointList;

    public static ResGetUserPointDTO of(Integer statusCode, String message, List<GetPointDTO> pointList){
        ResGetUserPointDTO body = new ResGetUserPointDTO();

        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setPointList(pointList);

        return body;
    }

    @Getter
    public static class GetPointDTO{
        private LocalDateTime getDate;
        private String getType;
        private Integer point;

        public GetPointDTO(PointLog pointLog){
            this.getDate = pointLog.getDistribute_time();
            this.getType = pointLog.getPe()!=null? "선수평가":"경기에측";
            this.point = pointLog.getPoint();
        }
    }
}
