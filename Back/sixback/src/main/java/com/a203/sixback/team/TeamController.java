package com.a203.sixback.team;

import com.a203.sixback.db.repo.CoachRepo;
import com.a203.sixback.db.repo.PlayerRepo;
import com.a203.sixback.db.repo.TeamRepo;
import com.a203.sixback.team.res.PlayerDetRes;
import com.a203.sixback.team.res.TeamDetRes;
import com.a203.sixback.team.res.TeamPlayersRes;
import com.a203.sixback.team.res.TeamRankRes;
import com.a203.sixback.team.vo.*;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    @Value("${API-KEY}")
    public String apiKey;

    @GetMapping("/{teamId}")
    public ResponseEntity<BaseResponseBody> getTeamInfo(){
        return null;
    }
    // 팀선수, 감독 조회
    @GetMapping("/{teamId}/players")
    public ResponseEntity<BaseResponseBody> getTeamPlayers(@PathVariable("teamId") int teamId){
        TeamPlayers result = teamService.getTeamPlayers(teamId);
        return ResponseEntity.status(200).body(TeamPlayersRes.of(200,"Success",result));
    }
    // 팀 세부정보 조회
    @GetMapping("/{teamId}/details")
    public ResponseEntity<BaseResponseBody> getTeamDetails(@PathVariable("teamId") int teamId){
        TeamDetVO result = teamService.getTeamDetails(teamId);
        return ResponseEntity.status(200).body(TeamDetRes.of(200,"Success",result));
    }
    // 전체 팀 순위조회
    @GetMapping("/ranks")
    public ResponseEntity<BaseResponseBody> getTeamsRanks(){
        ArrayList<TeamInfo> result = teamService.getTeamRanks();
        return ResponseEntity.status(200).body(TeamRankRes.of(200,"Success",result));
    }
    // 선수 세부정보 조회
    @GetMapping("/players/{playerId}")
    public ResponseEntity<BaseResponseBody> getPlayerDetails(@PathVariable("playerId") long playerId){
        PlayerDetVO result = teamService.getPlayerDetails(playerId);
        return ResponseEntity.status(200).body(PlayerDetRes.of(200,"Success",result));
    }
}
