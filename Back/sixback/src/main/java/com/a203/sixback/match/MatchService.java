package com.a203.sixback.match;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.enums.History;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.enums.TeamType;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.match.vo.*;
import com.a203.sixback.team.vo.MatchVO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchesRepo matchesRepo;
    private final MatchDetRepo matchDetRepo;
    private final MatchHistoryRepo matchHistoryRepo;
    private final PlayerMatchRepo playerMatchRepo;
    private final PlayerEvaluateRepo playerEvaluateRepo;
    private final PlayerRepo playerRepo;
    private final UserRepo userRepo;
    private final PredictRepo predictRepo;
    private final TeamRepo teamRepo;
    public List<MatchStatusVO> getMatchesByRound(int round) {
        List<Matches> matches = matchesRepo.findAllByRound(round);
        List<MatchStatusVO> result = new ArrayList<>();
        for(Matches match : matches){
            MatchVO matchVO = MatchVO.builder()
                    .matchId(match.getId())
                    .home(match.getHome().getName())
                    .homeImage(match.getHome().getImage())
                    .away(match.getAway().getName())
                    .awayImage(match.getAway().getImage())
                    .homeScore(match.getHomeScore())
                    .date(match.getMatchDate().toString())
                    .awayScore(match.getAwayScore())
                    .stadium(match.getStadium())
                    .build();
            result.add(new MatchStatusVO(matchVO, match.getMatchStatus()));
        }
        return result;
    }
    public List<MatchStatusVO> getMatchesByDate(int year, int month, int day){
        List<Matches> matches = matchesRepo.findAllByYearAndMonthAndDay(year,month,day);
        List<MatchStatusVO> result = new ArrayList<>();
        for(Matches match : matches){
            MatchVO matchVO = MatchVO.builder()
                    .matchId(match.getId())
                    .home(match.getHome().getName())
                    .homeImage(match.getHome().getImage())
                    .away(match.getAway().getName())
                    .awayImage(match.getAway().getImage())
                    .homeScore(match.getHomeScore())
                    .date(match.getMatchDate().toString())
                    .awayScore(match.getAwayScore())
                    .stadium(match.getStadium())
                    .build();
            result.add(new MatchStatusVO(matchVO, match.getMatchStatus()));
        }
        return result;
    }
    public List<MatchStatusVO> getMatchesByMonth(int year, int month) {
        List<Matches> matches = matchesRepo.findAllByYearAndMonth(year,month);
        List<MatchStatusVO> result = new ArrayList<>();
        for(Matches match : matches){
            MatchVO matchVO = MatchVO.builder()
                    .matchId(match.getId())
                    .home(match.getHome().getName())
                    .homeImage(match.getHome().getImage())
                    .away(match.getAway().getName())
                    .awayImage(match.getAway().getImage())
                    .homeScore(match.getHomeScore())
                    .date(match.getMatchDate().toString())
                    .awayScore(match.getAwayScore())
                    .stadium(match.getStadium())
                    .build();
            result.add(new MatchStatusVO(matchVO, match.getMatchStatus()));
        }
        return result;
    }

    public List<LineUpVO> getLineUps(long matchId) {
        List<LineUpVO> result = new ArrayList<>();
        List<MatchDet> matchDetList = matchDetRepo.findAllByMatches_Id(matchId);
        LineUpVO home = new LineUpVO();
        LineUpVO away = new LineUpVO();
        for(int i=0;i<2;i++){
            String team = matchDetList.get(i).getTeamType().toString();
            String formation = matchDetList.get(i).getFormation();

            if(team.equals("HOME")){
                home.setTeamType(team);
                home.setFormation(formation);
            }
            else{
                away.setTeamType(team);
                away.setFormation(formation);
            }
        }

        List<PlayerMatch> playerMatchList = playerMatchRepo.findAllByMatches_Id(matchId);
        List<LineUp> homeLineUp = new ArrayList<>();
        List<LineUp> awayLineUp = new ArrayList<>();
        for(int i=0;i<playerMatchList.size();i++){
            if(playerMatchList.get(i).getTeam().equals("HOME")){
                homeLineUp.add(new LineUp(playerMatchList.get(i).getPlayer().getName(),
                        playerMatchList.get(i).getPlayer().getNumber(),
                        playerMatchList.get(i).getPosition()));
            }
            else{
                awayLineUp.add(new LineUp(playerMatchList.get(i).getPlayer().getName(),
                        playerMatchList.get(i).getPlayer().getNumber(),
                        playerMatchList.get(i).getPosition()));
            }
        }
        home.setLineUpList(homeLineUp);
        away.setLineUpList(awayLineUp);

        result.add(home);
        result.add(away);
        return result;
    }

    @Value("${API-KEY}")
    public String apiKey;

    public JSONArray getMatchEvent(long matchId) throws Exception{
        JSONArray jsonArray = new JSONArray();
        String str = "https://apiv3.apifootball.com/?action=get_events&match_id="+matchId+"&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonArray = (JSONArray) JSONValue.parseWithException(isr);
        return jsonArray;
    }

    public List<PlayerMatchVO> getAllPlayerMatch(long match_id) {
        List<PlayerMatch> playerMatchList = playerMatchRepo.findAllByMatches_Id(match_id);
        List<PlayerMatchVO> playerMatchVOList = new ArrayList<>();
        for(PlayerMatch pm : playerMatchList){
            if(pm.getPlayer() == null) continue;
            PlayerMatchVO vo = PlayerMatchVO.builder()
                    .goal(pm.getGoal())
                    .assist(pm.getAssist())
                    .shot(pm.getShot())
                    .shotOn(pm.getShotOn())
                    .pass(pm.getPass())
                    .passOn(pm.getPassOn())
                    .foul(pm.getFoul())
                    .crossed(pm.getCrossed())
                    .crossedOn(pm.getCrossedOn())
                    .dribble(pm.getDribble())
                    .dribbleOn(pm.getDribbleOn())
                    .tackle(pm.getTackle())
                    .clear(pm.getClear())
                    .yellow(pm.getYellow())
                    .red(pm.getRed())
                    .match_id(pm.getMatches().getId())
                    .player_id(pm.getPlayer().getId())
                    .playerName(pm.getPlayer().getName())
                    .team(pm.getTeam())
                    .build();
            playerMatchVOList.add(vo);
        }
        return playerMatchVOList;
    }

    public void updatePlayerEvaluation(PlayerEvaluateVO playerEvaluateVO) {
        Matches matches = matchesRepo.findById(playerEvaluateVO.getMatchId()).get();
        Player player = playerRepo.findById(playerEvaluateVO.getPlayerId()).get();
        User user = userRepo.findByEmail(playerEvaluateVO.getUserEmail());
        PlayerEvaluate pe = playerEvaluateRepo.findByMatches_IdAndUser_IdAndPlayer_Id(matches.getId(), user.getId(), player.getId());
        if(pe != null){ //기존거 바꾸기
            pe.setScore(playerEvaluateVO.getScore());
            playerEvaluateRepo.save(pe);
        }else{
            playerEvaluateRepo.save(new PlayerEvaluate(matches,player,playerEvaluateVO.getScore(),user));
        }
    }


    public void matchPredict(MatchPredictVO matchPredictVO) {
        Matches matches = matchesRepo.findById(matchPredictVO.getMatchId()).get();
        User user = userRepo.findById(matchPredictVO.getUserId()).get();

        String predict = matchPredictVO.getWhereWin();
        MatchResult _predict = "HOME".equals(predict)?
                MatchResult.HOME_WIN:"DRAW".equals(predict)?
                MatchResult.DRAW:MatchResult.AWAY_WIN;

        Predict newMatchPredict = new Predict(user, matches, _predict);
        predictRepo.save(newMatchPredict);
    }

    public List<MatchPredictVO> getAllMatchPredict(long matchId) {
        List<Predict> matchPredictList = predictRepo.findAllByMatches_Id(matchId);
        List<MatchPredictVO> result = new ArrayList<>();
        for(Predict mp : matchPredictList){
            String nickname = userRepo.findById(mp.getUser().getId()).get().getNickname();

            String predict = mp.getMatchResult().toString();
            String _predict = "HOME_WIN".equals(predict)?
                    "HOME":"DRAW".equals(predict)?
                    "DRAW":"AWAY";

            MatchPredictVO vo = MatchPredictVO.builder()
                    .matchId(matchId)
                    .userNickname(nickname)
                    .whereWin(_predict)
                    .build();
            result.add(vo);
        }
        return result;
    }

    public List<TeamBoardVO> getTeams() {
        List<TeamBoardVO> result = new ArrayList<>();
        List<Team> teams = teamRepo.findAll();
        for(Team team : teams){
            result.add(new TeamBoardVO(team.getId(), team.getName(), team.getImage()));
        }
        return result;
    }

    public List<MatchBoardVO> getMatchBoards(int roundId) {
        List<MatchBoardVO> result = new ArrayList<>();
        List<Matches> matches = matchesRepo.findAllByRound(roundId);
        for(Matches match : matches){
            String name = "[" +match.getRound()+"] "+match.getHome().getName()+ " VS " + match.getAway().getName();
            result.add(new MatchBoardVO(match.getId(), name));
        }
        return result;
    }
    public MatchStatusVO getMatchDetail(long id) {
        Matches match = matchesRepo.findById(id).get();
        MatchVO matchVO = MatchVO.builder()
                .matchId(match.getId())
                .home(match.getHome().getName())
                .homeImage(match.getHome().getImage())
                .away(match.getAway().getName())
                .awayImage(match.getAway().getImage())
                .homeScore(match.getHomeScore())
                .date(match.getMatchDate().toString())
                .awayScore(match.getAwayScore())
                .stadium(match.getStadium())
                .build();
        MatchStatusVO result = new MatchStatusVO(matchVO, match.getMatchStatus());
        return result;
    }

    // 라인업 나올시에 저장하는 거
    public void saveLineUps(Long matchId) throws Exception{
        JSONObject jsonObject = new JSONObject();

        String str = "https://apiv3.apifootball.com/?action=get_events&match_id="+matchId+"APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonObject = (JSONObject) JSONValue.parseWithException(isr);
        JSONObject lineups = (JSONObject) jsonObject.get("lineup");
        JSONObject homeLineups = (JSONObject) lineups.get("home");
        JSONObject awayLineups = (JSONObject) lineups.get("away");
        JSONArray homeStarts = (JSONArray) homeLineups.get("starting_lineups");
        JSONArray homeSubsPlayer = (JSONArray) homeLineups.get("substitutes");
        JSONArray awayStarts = (JSONArray) awayLineups.get("starting_lineups");
        JSONArray awaySubsPlayer = (JSONArray) awayLineups.get("substitutes");
        String homeFormation = jsonObject.get("match_hometeam_system").toString();
        String awayFormation = jsonObject.get("match_awayteam_system").toString();


        Matches savedMatches = matchesRepo.findById(matchId).get();
        // 라인업 포메이션 저장
        matchDetRepo.save(MatchDet.builder()
                .matches(savedMatches)
                .teamType(TeamType.HOME)
                .formation(homeFormation)
                .build());
        matchDetRepo.save(MatchDet.builder()
                .matches(savedMatches)
                .teamType(TeamType.AWAY)
                .formation(awayFormation)
                .build());

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

    // 경기끝나고 발생하는 이벤트 저장
    public void savePlayerMatch(long matchId) throws Exception{
        // 경기 결과에 따른 선수 세부 스탯 저장
        JSONObject jsonObject = new JSONObject();
        String str = "https://apiv3.apifootball.com/?action=get_statistics&match_id="+matchId+"&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonObject = (JSONObject) JSONValue.parseWithException(isr);
        JSONObject j2 = (JSONObject)jsonObject.get(Long.toString(matchId));
        JSONArray playersStats = (JSONArray) j2.get("player_statistics");
        for(int i=0;i<playersStats.size();i++){
            JSONObject playerStat = (JSONObject)playersStats.get(i);
            Optional<Player> player = playerRepo.findById(Long.parseLong(playerStat.get("player_key").toString()));

            if(!player.isPresent()){
                continue;
            }
            if(!playerStat.get("player_minutes_played").toString().equals("0")){
                Player savedPlayer = player.get();
                savedPlayer.addPlayerStat(Integer.parseInt(playerStat.get("player_goals").toString()),Integer.parseInt(playerStat.get("player_assists").toString()));
                playerRepo.save(savedPlayer);
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
    public void saveTeamMatch(long matchId) throws Exception{
        JSONObject jsonObject = new JSONObject();
        Matches savedMatches = matchesRepo.findById(matchId).get();
        String str = "https://apiv3.apifootball.com/?action=get_events&match_id="+matchId+"&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonObject = (JSONObject) ((JSONArray)JSONValue.parseWithException(isr)).get(0);
        int homeScore = Integer.parseInt(jsonObject.get("match_hometeam_score").toString());
        int awayScore = Integer.parseInt(jsonObject.get("match_awayteam_score").toString());
        savedMatches.setScore(homeScore, awayScore);
        Team homeTeam = savedMatches.getHome();
        Team awayTeam = savedMatches.getAway();
        homeTeam.addTeamInfo(homeScore, awayScore);
        awayTeam.addTeamInfo(awayScore, homeScore);
        teamRepo.save(homeTeam);
        teamRepo.save(awayTeam);
        matchesRepo.save(savedMatches);

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
    }

    public void saveGoals(JSONObject jsonObject, long matchId){
        Matches savedMatches = matchesRepo.findById(matchId).get();
        String time =jsonObject.get("time").toString();
        String man = "";
        String man2 = "";
        String type;
        String info = jsonObject.get("info").toString();
        if (jsonObject.get("home_scorer").toString().equals("")) {
            man = jsonObject.get("away_scorer").toString();
            man2 = jsonObject.get("away_assist").toString();
            type = "AWAY";
        } else {
            man = jsonObject.get("home_scorer").toString();
            man2 = jsonObject.get("home_assist").toString();
            type = "HOME";
        }
        matchHistoryRepo.save(MatchHistory.builder()
                        .matches(savedMatches)
                        .history(History.GOAL)
                        .mainName(man)
                        .subName(man2)
                        .time(time)
                        .info(info)
                        .teamType(TeamType.valueOf(type))
                        .build());
    }
    public void saveCards(JSONObject card, long matchId){
        Matches savedMatches = matchesRepo.findById(matchId).get();
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
    public void saveHomeSub(JSONObject sub, long matchId){
        String time = sub.get("time").toString();
        Matches savedMatches = matchesRepo.findById(matchId).get();
        String subMans[] =  sub.get("substitution").toString().split("\\|");
        matchHistoryRepo.save(MatchHistory.builder()
                .matches(savedMatches)
                .mainName(subMans[0])
                .subName(subMans[1])
                .time(time)
                .teamType(TeamType.HOME)
                .history(History.SUB)
                .build());
    }
    public void saveAwaySub(JSONObject sub, long matchId){
        String time = sub.get("time").toString();
        Matches savedMatches = matchesRepo.findById(matchId).get();
        String subMans[] =  sub.get("substitution").toString().split("\\|");
        matchHistoryRepo.save(MatchHistory.builder()
                .matches(savedMatches)
                .mainName(subMans[0])
                .subName(subMans[1])
                .time(time)
                .teamType(TeamType.AWAY)
                .history(History.SUB)
                .build());
    }
}
