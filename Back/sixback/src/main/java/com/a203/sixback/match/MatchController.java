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
    @GetMapping("/player/save")
    public void saveMatchPlayer() throws Exception{

        List<Long> matchesList = matchesRepo.findAllByStatus();
        for(long matchId : matchesList){
            JSONObject jsonObject = new JSONObject();
            String str = "https://apiv3.apifootball.com/?action=get_statistics&match_id="+matchId+"&APIkey=" + apiKey;
            URL url = new URL(str);
            InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
            jsonObject = (JSONObject) JSONValue.parseWithException(isr);
            System.out.println(jsonObject);
            JSONObject j2 = (JSONObject)jsonObject.get(Long.toString(matchId));
            System.out.println(j2);
            JSONArray playersStats = (JSONArray) j2.get("player_statistics");
            for(int i=0;i<playersStats.size();i++){
                JSONObject playerStat = (JSONObject)playersStats.get(i);
                Optional<Player> player = playerRepo.findById(Long.parseLong(playerStat.get("player_key").toString()));
                if(!player.isPresent()){
                    continue;
                }
                PlayerMatch playerMatch = playerMatchRepo.findByMatches_IdAndPlayer_Id(matchId, player.get().getId());
                playerMatch.setGoal(Integer.parseInt(playerStat.get("player_goals").toString()));
                playerMatch.setAssist(Integer.parseInt(playerStat.get("player_assists").toString()));
                playerMatch.setClear(Integer.parseInt(playerStat.get("player_clearances").toString()));
                playerMatch.setCrossed(Integer.parseInt(playerStat.get("player_total_crosses").toString()));
                playerMatch.setCrossedOn(Integer.parseInt(playerStat.get("player_acc_crosses").toString()));
                playerMatch.setDribble(Integer.parseInt(playerStat.get("player_dribble_attempts").toString()));
                playerMatch.setDribbleOn(Integer.parseInt(playerStat.get("player_dribble_succ").toString()));
                playerMatch.setExpertRate(Integer.parseInt(playerStat.get("player_rating").toString()));
                playerMatch.setFoul(Integer.parseInt(playerStat.get("player_fouls_commited").toString()));
                playerMatch.setPass(Integer.parseInt(playerStat.get("player_passes").toString()));
                playerMatch.setPassOn(Integer.parseInt(playerStat.get("player_passes_acc").toString()));
                playerMatch.setYellow(Integer.parseInt(playerStat.get("player_yellowcards").toString()));
                playerMatch.setRed(Integer.parseInt(playerStat.get("player_redcards").toString()));
                playerMatch.setShot(Integer.parseInt(playerStat.get("player_shots_total").toString()));
                playerMatch.setShotOn(Integer.parseInt(playerStat.get("player_shots_on_goal").toString()));
                playerMatch.setTackle(Integer.parseInt(playerStat.get("player_tackles").toString()));
                playerMatch.setTeam(playerStat.get("team_name").toString().toUpperCase());
                playerMatchRepo.save(playerMatch);
            }
        }



    }
    private final TeamRepo teamRepo;
    private final PlayerRepo playerRepo;
    private final MatchesRepo matchesRepo;
    private final MatchHistoryRepo matchHistoryRepo;
    private final MatchDetRepo matchDetRepo;
    private final PlayerMatchRepo playerMatchRepo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @GetMapping("/match")
    public void getMatchData() throws Exception {
        JSONArray jsonArray = new JSONArray();
        String str = "https://apiv3.apifootball.com/?action=get_events&from=2022-08-01&to=2022-11-14&league_id=152&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonArray = (JSONArray) JSONValue.parseWithException(isr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = ((JSONObject) jsonArray.get(i));
            long matchId = Long.parseLong(jsonObject.get("match_id").toString());
            String match_date = jsonObject.get("match_date") + " " + jsonObject.get("match_time");
            String matchStatus = jsonObject.get("match_status").toString().equals("Finished") ? "FIN" : "READY";
            int homeScore =0;
            int awayScore=0;
            int home = Integer.parseInt(jsonObject.get("match_hometeam_id").toString());
            int away = Integer.parseInt(jsonObject.get("match_awayteam_id").toString());
            if(matchStatus.equals("FIN")){
                homeScore = Integer.parseInt(jsonObject.get("match_hometeam_score").toString());
                awayScore = Integer.parseInt(jsonObject.get("match_awayteam_score").toString());
            }
            else{
                if(jsonObject.get("match_status").toString().equals("Postponed")){
                    matchStatus = "DELAY";
                }
            }
            int round = Integer.parseInt(jsonObject.get("match_round").toString());
            String stadium = jsonObject.get("match_stadium").toString();
            String referee = jsonObject.get("match_referee").toString();
            LocalDateTime dateTime = LocalDateTime.parse(match_date, formatter);
            Team homeTeam = teamRepo.findById(home).get();
            Team awayTeam = teamRepo.findById(away).get();
            Matches matches = Matches.builder()
                    .id(matchId)
                    .home(homeTeam)
                    .away(awayTeam)
                    .awayScore(awayScore)
                    .homeScore(homeScore)
                    .referee(referee)
                    .stadium(stadium)
                    .round(round)
                    .matchStatus(MatchStatus.valueOf(matchStatus))
                    .matchDate(dateTime.plusHours(7))
                    .build();
            Matches savedMatches = matchesRepo.save(matches);

            //매치 History 가져오기
            JSONArray goals = (JSONArray) jsonObject.get("goalscorer");
            JSONArray cards = (JSONArray) jsonObject.get("cards");
            JSONObject lineups = (JSONObject) jsonObject.get("lineup");
            JSONObject subs = (JSONObject) jsonObject.get("substitutions");
            JSONArray homeSubs = (JSONArray) subs.get("home");
            JSONArray awaySubs = (JSONArray) subs.get("away");
            JSONObject homeLineups = (JSONObject) lineups.get("home");
            JSONObject awayLineups = (JSONObject) lineups.get("away");
            JSONArray homeStarts = (JSONArray) homeLineups.get("starting_lineups");
            JSONArray homeSubsPlayer = (JSONArray) homeLineups.get("substitutes");
            JSONArray awayStarts = (JSONArray) awayLineups.get("starting_lineups");
            JSONArray awaySubsPlayer = (JSONArray) awayLineups.get("substitutes");



            for (int j = 0; j < goals.size(); j++) {
                JSONObject goal = (JSONObject) goals.get(j);
                String time = goal.get("time").toString();
                String man = "";
                String man2 = "";
                String type;
                String info = goal.get("info").toString();
                if (goal.get("home_scorer").toString().equals("")) {
                    man = goal.get("away_scorer").toString();
                    man2 = goal.get("away_assist").toString();
                    type = "AWAY";
                } else {
                    man = goal.get("home_scorer").toString();
                    man2 = goal.get("home_assist").toString();
                    type = "HOME";
                }
                matchHistoryRepo.save(MatchHistory.builder()
                        .matches(savedMatches)
                        .history(History.GOAL)
                        .info(info)
                        .time(time)
                        .teamType(TeamType.valueOf(type))
                        .mainName(man)
                        .subName(man2)
                        .build());
            }
            for (int j = 0; j < cards.size(); j++) {
                JSONObject card = (JSONObject) cards.get(j);
                String time = card.get("time").toString();
                String man = "";
                String type;
                String color = card.get("card").toString().equals("yellow card") ? "YELLOW" : "RED";
                String info = card.get("info").toString();
                if (card.get("home_fault").toString().equals("")) {
                    man = card.get("away_fault").toString();
                    type = "AWAY";
                } else {
                    man = card.get("home_fault").toString();
                    type = "HOME";
                }
                matchHistoryRepo.save(MatchHistory.builder()
                        .matches(savedMatches)
                        .history(History.valueOf(color))
                        .info(info)
                        .time(time)
                        .teamType(TeamType.valueOf(type))
                        .mainName(man)
                        .build());
            }
            for (int j = 0; j < homeSubs.size(); j++) {
                JSONObject sub = (JSONObject) homeSubs.get(j);
                String time = sub.get("time").toString();
                String subMans[] = sub.get("substitution").toString().split("\\|");

                matchHistoryRepo.save(MatchHistory.builder()
                        .matches(savedMatches)
                        .mainName(subMans[0])
                        .subName(subMans[1])
                        .time(time)
                        .teamType(TeamType.HOME)
                        .history(History.SUB)
                        .build());
            }
            for (int j = 0; j < awaySubs.size(); j++) {
                JSONObject sub = (JSONObject) awaySubs.get(j);
                String time = sub.get("time").toString();
                String subMans[] = sub.get("substitution").toString().split("\\|");

                matchHistoryRepo.save(MatchHistory.builder()
                        .matches(savedMatches)
                        .mainName(subMans[0])
                        .subName(subMans[1])
                        .time(time)
                        .teamType(TeamType.AWAY)
                        .history(History.SUB)
                        .build());
            }
            // MatchDetail 저장
            int shot[][] = new int[1][2];
            int shotOn[][] = new int[1][2];
            int foul[][] = new int[1][2];
            int corner[][] = new int[1][2];
            int offside[][] = new int[1][2];
            String poss[][] = new String[1][2];
            int yellow[][] = new int[1][2];
            int red[][] = new int[1][2];
            int save[][] = new int[1][2];
            int pass[][] = new int[1][2];
            int suc[][] = new int[1][2];
            int penalty[][] = new int[1][2];
            String formation[][] = new String[1][2];
            formation[0][0] = jsonObject.get("match_hometeam_system").toString();
            formation[0][1] = jsonObject.get("match_awayteam_system").toString();
            JSONArray dets = (JSONArray)jsonObject.get("statistics");
            for(int j=0;j<dets.size();j++){
                JSONObject det = (JSONObject) dets.get(j);
                String type = det.get("type").toString();

                if(type.equals("Penalty")){
                    if(!det.get("home").toString().equals("")){
                        penalty[0][0] = Integer.parseInt(det.get("home").toString());
                        penalty[0][1] = Integer.parseInt(det.get("away").toString());
                    }

                }
                else if(type.equals("Shots Total")){
                    shot[0][0] = Integer.parseInt(det.get("home").toString());
                    shot[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Shots On Goal")){
                    shotOn[0][0] = Integer.parseInt(det.get("home").toString());
                    shotOn[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Fouls")){
                    foul[0][0] = Integer.parseInt(det.get("home").toString());
                    foul[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Corners")){
                    corner[0][0] = Integer.parseInt(det.get("home").toString());
                    corner[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Offsides")){
                    offside[0][0] = Integer.parseInt(det.get("home").toString());
                    offside[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Ball Possession")){
                    poss[0][0] = det.get("home").toString();
                    poss[0][1] = det.get("away").toString();
                }
                else if(type.equals("Saves")){
                    save[0][0] = Integer.parseInt(det.get("home").toString());
                    save[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Passes Total")){
                    pass[0][0] = Integer.parseInt(det.get("home").toString());
                    pass[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Yellow Cards")){
                    yellow[0][0] = Integer.parseInt(det.get("home").toString());
                    yellow[0][1] = Integer.parseInt(det.get("away").toString());
                }
                else if(type.equals("Red Cards")){
                    red[0][0] = Integer.parseInt(det.get("home").toString());
                    red[0][1] = Integer.parseInt(det.get("away").toString());
                }

            }
            for(int t=0;t<=1;t++){
                String type = t==0? "HOME" : "AWAY";
                matchDetRepo.save(MatchDet.builder()
                                .foul(foul[0][t])
                                .pass(pass[0][t])
                                .corner(corner[0][t])
                                .matches(savedMatches)
                                .offside(offside[0][t])
                                .penalty(penalty[0][t])
                                .passOn(suc[0][t])
                                .possession(poss[0][t])
                                .formation(formation[0][t])
                                .red(red[0][t])
                                .yellow(yellow[0][t])
                                .shot(shot[0][t])
                                .shotOn(shotOn[0][t])
                                .save(save[0][t])
                                .teamType(TeamType.valueOf(type))
                        .build());
            }

            //매치 라인업 저장하기
            // 홈 스타트 멤버
            for(int t=0;t<homeStarts.size();t++){
                JSONObject start = (JSONObject) homeStarts.get(t);
                Optional<Player> player = playerRepo.findById(Long.parseLong(start.get("player_key").toString()));
                playerMatchRepo.save(PlayerMatch.builder()
                        .position(Integer.parseInt(start.get("lineup_position").toString()))
                        .player(player.isPresent()?player.get():null)
                        .team("HOME")
                        .matches(savedMatches)
                        .build());
            }
            // 홈 서브 멤버
            for(int t=0;t<homeSubsPlayer.size();t++){
                JSONObject start = (JSONObject) homeSubsPlayer.get(t);
                Optional<Player> player = playerRepo.findById(Long.parseLong(start.get("player_key").toString()));
                playerMatchRepo.save(PlayerMatch.builder()
                        .position(Integer.parseInt(start.get("lineup_position").toString()))
                        .player(player.isPresent()?player.get():null)
                        .matches(savedMatches)
                        .team("HOME")
                        .build());
            }
            // 어웨이 스타트 멤버
            for(int t=0;t<awayStarts.size();t++){
                JSONObject start = (JSONObject) awayStarts.get(t);
                Optional<Player> player = playerRepo.findById(Long.parseLong(start.get("player_key").toString()));
                playerMatchRepo.save(PlayerMatch.builder()
                        .position(Integer.parseInt(start.get("lineup_position").toString()))
                        .player(player.isPresent()?player.get():null)
                        .team("AWAY")
                        .matches(savedMatches)
                        .build());
            }
            // 어웨이 서브 멤버
            for(int t=0;t<awaySubsPlayer.size();t++){
                JSONObject start = (JSONObject) awaySubsPlayer.get(t);
                Optional<Player> player = playerRepo.findById(Long.parseLong(start.get("player_key").toString()));
                playerMatchRepo.save(PlayerMatch.builder()
                        .position(Integer.parseInt(start.get("lineup_position").toString()))
                        .player(player.isPresent()?player.get():null)
                        .matches(savedMatches)
                        .team("AWAY")
                        .build());
            }
        }
    }

}
