package com.a203.sixback.match;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.enums.History;
import com.a203.sixback.db.enums.MatchStatus;
import com.a203.sixback.db.enums.TeamType;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.match.res.AllMatchRes;
import com.a203.sixback.match.res.AllPlayersRes;
import com.a203.sixback.match.vo.LineUpVO;
import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {
    @Value("${API-KEY}")
    public String apiKey;
    private final MatchService matchService;

    @GetMapping("/round")
    public ResponseEntity<BaseResponseBody> getMatchesByRound(@RequestParam int round) throws Exception{
        List<MatchStatusVO> result = matchService.getMatchesByRound(round);
        return ResponseEntity.status(200).body(AllMatchRes.of(200,"Success",result));
    }
    @GetMapping("/dates")
    public ResponseEntity<BaseResponseBody> getMatchesByMonth(@RequestParam int year, @RequestParam int month){
        List<MatchStatusVO> result = matchService.getMatchesByMonth(year,month);
        return ResponseEntity.status(200).body(AllMatchRes.of(200,"Success",result));
    }

    @GetMapping("/{matchId}/lineUps")
    public ResponseEntity<BaseResponseBody> getLineUps(@PathVariable("matchId") long matchId){
        List<LineUpVO> result = matchService.getLineUps(matchId);
        return ResponseEntity.status(200).body(AllPlayersRes.of(200,"Success",result));
    }
}
