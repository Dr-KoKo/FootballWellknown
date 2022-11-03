package com.a203.sixback.match;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.enums.History;
import com.a203.sixback.db.enums.MatchStatus;
import com.a203.sixback.db.enums.TeamType;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.match.res.*;
import com.a203.sixback.match.vo.*;
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
    @GetMapping("/match/{id}")
    public ResponseEntity<BaseResponseBody> getMatchDetail(@PathVariable long id){
        MatchStatusVO result = matchService.getMatchDetail(id);
        return ResponseEntity.status(200).body(MatchDetailRes.of(200,"Success",result));
    }

    @GetMapping("/{matchId}/lineUps")
    public ResponseEntity<BaseResponseBody> getLineUps(@PathVariable("matchId") long matchId){
        List<LineUpVO> result = matchService.getLineUps(matchId);
        return ResponseEntity.status(200).body(AllPlayersRes.of(200,"Success",result));
    }
    @GetMapping("/statistics/{matchId}")
    public ResponseEntity<BaseResponseBody> getPlayerMatches(@PathVariable long matchId){
        StatisticsVO result = matchService.getAllPlayerMatch(matchId);
        return ResponseEntity.status(200).body(PlayerMatchRes.of(200,"Success",result));
    }

    @PostMapping("/predict/player")
    public ResponseEntity<BaseResponseBody> evaluatePlayer(@RequestBody PlayerEvaluateVO playerEvaluateVO){
        matchService.updatePlayerEvaluation(playerEvaluateVO);
        return ResponseEntity.status(200).body(null);
    }

    @PostMapping("/predict/match")
    public ResponseEntity<BaseResponseBody> matchPredict(@RequestBody MatchPredictVO matchPredictVO) {
        matchService.matchPredict(matchPredictVO);
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/predict/match/my/{userEmail}/{matchId}")
    public ResponseEntity<BaseResponseBody> myMatchPredict(@PathVariable String userEmail, @PathVariable long matchId){
        MatchPredictVO result = matchService.getMyMatchPredict(userEmail, matchId);
        return ResponseEntity.status(200).body(MatchPredictRes.of(200,"Success",result));
    }

    @GetMapping("/predict/match/all/{matchId}")
    public ResponseEntity<BaseResponseBody> getAllMatchPredict(@PathVariable long matchId){
        List<MatchPredictVO> result = matchService.getAllMatchPredict(matchId);
        return ResponseEntity.status(200).body(AllMatchPredictRes.of(200,"Success",result));
    }

    @GetMapping("/boards/teams")
    public ResponseEntity<BaseResponseBody> getTeams(){
        List<TeamBoardVO> result = matchService.getTeams();
        return ResponseEntity.status(200).body(AllTeamBoardRes.of(200,"Success",result));
    }

    @GetMapping("/boards/rounds/{roundId}")
    public ResponseEntity<BaseResponseBody> getMatchBoards(@PathVariable("roundId") int roundId){
        List<MatchBoardVO> result = matchService.getMatchBoards(roundId);
        return ResponseEntity.status(200).body(AllMatchBoardRes.of(200,"Success",result));
    }
}
