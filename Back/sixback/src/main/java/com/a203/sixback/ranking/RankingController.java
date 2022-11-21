package com.a203.sixback.ranking;

import com.a203.sixback.db.redis.RankingCacheRepository;
import com.a203.sixback.ranking.res.ResRankingDTO;
import com.a203.sixback.ranking.res.ResponseRankingDTO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final RankingCacheRepository rankingCacheRepository;

    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getRankingList() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingService.getRankingList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

    @GetMapping("/daily")
    public ResponseEntity<? extends BaseResponseBody> getDailyRankingList() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingService.getDailyRankingList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

    @GetMapping("/weekly")
    public ResponseEntity<? extends BaseResponseBody> getWeeklyRankingList() {
        List<ResponseRankingDTO> result;
        try {
            result = rankingService.getWeeklyRankingList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponseBody.of(400, "잘못된 요청입니다."));
        }
        return ResponseEntity.ok().body(ResRankingDTO.of(200, "성공", result));
    }

    @PostMapping("/test")
    public ResponseEntity<BaseResponseBody> registerScore(){
        rankingCacheRepository.addScore("GOO3837678538", 10);
        rankingCacheRepository.addScore("GOO8838923192",5);
        rankingCacheRepository.addScore("GOO3285927599",8);
        rankingCacheRepository.addScore("GOO4685562230",15);
        rankingCacheRepository.addScore("GOO3285927599",15);
        return ResponseEntity.ok().body(BaseResponseBody.of(200, "성공"));
    }

}
