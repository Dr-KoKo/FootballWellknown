package com.a203.sixback.ranking;

import com.a203.sixback.db.enums.DayType;
import com.a203.sixback.db.redis.RankingCacheRepository;
import com.a203.sixback.ranking.res.ResRankingDTO;
import com.a203.sixback.ranking.res.ResponseRankingDTO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final RankingCacheRepository rankingCacheRepository;

    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getRanking() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingCacheRepository.getRankingList(DayType.ALL);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

    @GetMapping("/daily")
    public ResponseEntity<? extends BaseResponseBody> getDailyRanking() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingCacheRepository.getRankingList(DayType.DAILY);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

    @GetMapping("/weekly")
    public ResponseEntity<? extends BaseResponseBody> getWeeklyRanking() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingCacheRepository.getRankingList(DayType.WEEKLY);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

}
