package com.a203.sixback.team;

import com.a203.sixback.db.entity.Team;
import com.a203.sixback.db.repo.CoachRepo;
import com.a203.sixback.db.repo.PlayerRepo;
import com.a203.sixback.db.repo.TeamRepo;
import com.a203.sixback.team.res.*;
import com.a203.sixback.team.vo.*;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
    // 팀이름으로 팀 id 조회
    @PostMapping("/name")
    public ResponseEntity<BaseResponseBody> getTeamId(@RequestBody String name) {
        TeamInfo teamInfo = teamService.getTeam(name);
        return ResponseEntity.status(200).body(TeamRes.of(200,"Success",teamInfo));
    }
    @GetMapping("players/ranks")
    public ResponseEntity<BaseResponseBody> getPlayerRanks(){
        PlayerRankVO result = teamService.getPlayerRanks();
        return ResponseEntity.status(200).body(PlayerRankRes.of(200,"Success",result));

    }

}
