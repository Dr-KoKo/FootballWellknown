package com.a203.sixback.team;

import com.a203.sixback.team.res.TeamDetRes;
import com.a203.sixback.team.res.TeamPlayersRes;
import com.a203.sixback.team.res.TeamRankRes;
import com.a203.sixback.team.vo.TeamDetVO;
import com.a203.sixback.team.vo.TeamPlayers;
import com.a203.sixback.team.vo.TeamInfo;
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
//    private final TeamRepo teamRepo;
//    private final CoachRepo coachRepo;
//    private final PlayerRepo playerRepo;
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
    // 팀 순위 기록 저장
//    @GetMapping("/teamInfo")
//    public void teamInfo() throws IOException, ParseException{
//        JSONArray jsonArray = new JSONArray();
//        String str = "https://apiv3.apifootball.com/?action=get_standings&league_id=152&APIkey=" + apiKey;
//        URL url = new URL(str);
//        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
//        jsonArray = (JSONArray) JSONValue.parseWithException(isr);
//        for(int i=0;i<jsonArray.size();i++){
//            JSONObject jsonObject = ((JSONObject) jsonArray.get(i));
//            int arr[] = new int[5];
//            arr[0] = Integer.parseInt(jsonObject.get("overall_league_W").toString());
//            arr[1] = Integer.parseInt(jsonObject.get("overall_league_D").toString());
//            arr[2] = Integer.parseInt(jsonObject.get("overall_league_L").toString());
//            arr[3] = Integer.parseInt(jsonObject.get("overall_league_GF").toString());
//            arr[4] = Integer.parseInt(jsonObject.get("overall_league_GA").toString());
//
//            int teamId = Integer.parseInt(jsonObject.get("team_id").toString());
//            Team team = teamRepo.findById(teamId).get();
//            team.setTeamInfo(arr);
//            teamRepo.save(team);
//        }
//    }
////     전체 팀 정보 저장하기
//    @GetMapping("/team")
//    public void test() throws IOException, ParseException {
//        JSONArray jsonArray = new JSONArray();
//        String str = "https://apiv3.apifootball.com/?action=get_teams&league_id=152&APIkey=" + apiKey;
//        URL url = new URL(str);
//        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
//        jsonArray = (JSONArray) JSONValue.parseWithException(isr);
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject jsonObject = ((JSONObject) jsonArray.get(i));
//            int teamId = Integer.parseInt(jsonObject.get("team_key").toString());
//            String teamName = jsonObject.get("team_name").toString();
//            String teamImage = jsonObject.get("team_badge").toString();
//            JSONArray coaches = (JSONArray) jsonObject.get("coaches");
//            JSONArray players = (JSONArray) jsonObject.get("players");
//            String coachName = ((JSONObject) coaches.get(0)).get("coach_name").toString();
//
//            Team newTeam = Team.builder()
//                    .id(teamId)
//                    .name(teamName)
//                    .image(teamImage)
//                    .build();
//            teamRepo.save(newTeam);
//            Coach coach = Coach.builder()
//                    .age(0)
//                    .image(null)
//                    .country(null)
//                    .team(newTeam)
//                    .name(coachName)
//                    .build();
//            coachRepo.save(coach);
//            for (int j = 0; j < players.size(); j++) {
//                JSONObject player = ((JSONObject) players.get(j));
//                long playerId = Long.parseLong(player.get("player_id").toString());
//                String playerImage = player.get("player_image").toString();
//                String playerName = player.get("player_name").toString();
//                String pn = player.get("player_number").toString();
//                Integer playerNumber=null;
//                if(!pn.equals("")){
//                    playerNumber=Integer.parseInt(pn);
//                }
//                String position = player.get("player_type").toString();
//                int matchNum = Integer.parseInt(player.get("player_match_played").toString());
//                int goal = Integer.parseInt(player.get("player_goals").toString());
//                String birth = player.get("player_birthdate").toString();
//                Player newPlayer = Player.builder()
//                        .id(playerId)
//                        .team(newTeam)
//                        .goals(goal)
//                        .joinMatches(matchNum)
//                        .position(position)
//                        .image(playerImage)
//                        .name(playerName)
//                        .number(playerNumber)
//                        .birth(birth).build();
//                playerRepo.save(newPlayer);
//            }
//        }
//    }

}
