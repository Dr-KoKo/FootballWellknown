package com.a203.sixback.match;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.enums.History;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.enums.TeamType;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.match.vo.*;
import com.a203.sixback.team.vo.MatchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchesCustomRepo matchesCustomRepo;
    private final MatchesRepo matchesRepo;
    private final MatchDetRepo matchDetRepo;
    private final MatchHistoryRepo matchHistoryRepo;
    private final PlayerMatchRepo playerMatchRepo;
    private final PlayerEvaluateRepo playerEvaluateRepo;
    private final PlayerRepo playerRepo;
    private final UserRepo userRepo;
    private final PredictRepo predictRepo;
    private final MatchPredictRepo matchPredictRepo;
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
        List<Matches> matches = matchesCustomRepo.findAllByYearAndMonthOrderByMatch_Date(year,month);
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
            if(playerMatchList.get(i).getPosition()==0){
                continue;
            }
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

    public StatisticsVO getAllPlayerMatch(long match_id) {
        StatisticsVO result = new StatisticsVO();
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
        List<MatchDet> list = matchDetRepo.findAllByMatches_Id(match_id);
        Matches matches = matchesRepo.findById(match_id).get();
        for(MatchDet matchDet : list){

            if(matchDet.getTeamType().equals(TeamType.HOME)){
                result.setHomeDet(MatchDetVO.builder()
                        .name(matches.getHome().getName())
                        .image(matches.getHome().getImage())
                        .shot(matchDet.getShot())
                        .shotOn(matchDet.getShotOn())
                        .corner(matchDet.getCorner())
                        .formation(matchDet.getFormation())
                        .offside(matchDet.getOffside())
                        .penalty(matchDet.getPenalty())
                        .pass(matchDet.getPass())
                        .possession(matchDet.getPossession())
                        .save(matchDet.getSave())
                        .foul(matchDet.getFoul())
                        .red(matchDet.getRed())
                        .passOn(matchDet.getPassOn())
                        .yellow(matchDet.getYellow())
                        .build());
            } else if (matchDet.getTeamType().equals(TeamType.AWAY)) {
                result.setAwayDet(MatchDetVO.builder()
                        .name(matches.getAway().getName())
                        .image(matches.getAway().getImage())
                        .shot(matchDet.getShot())
                        .shotOn(matchDet.getShotOn())
                        .corner(matchDet.getCorner())
                        .formation(matchDet.getFormation())
                        .offside(matchDet.getOffside())
                        .penalty(matchDet.getPenalty())
                        .pass(matchDet.getPass())
                        .possession(matchDet.getPossession())
                        .save(matchDet.getSave())
                        .foul(matchDet.getFoul())
                        .red(matchDet.getRed())
                        .passOn(matchDet.getPassOn())
                        .yellow(matchDet.getYellow())
                        .build());

            }
        }
        result.setPlayers(playerMatchVOList);
        return result;
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
        User user = userRepo.findByEmail(matchPredictVO.getUserEmail());

        String predict = matchPredictVO.getWhereWin();
        MatchResult _predict = "HOME".equals(predict)?
                MatchResult.HOME:"DRAW".equals(predict)?
                MatchResult.DRAW:MatchResult.AWAY;

//        있으면 update 없으면 새거
        Predict old = predictRepo.findByMatches_IdAndUser_Id(matches.getId(), user.getId());
        if(old == null){
            Predict newMatchPredict = new Predict(user, matches, _predict);
            predictRepo.save(newMatchPredict);
        }else{
            old.setMatchResult(_predict);
            predictRepo.save(old);
        }
    }

    public List<MatchPredictVO> getAllMatchPredict(long matchId) {
        List<Predict> matchPredictList = predictRepo.findAllByMatches_Id(matchId);
        List<MatchPredictVO> result = new ArrayList<>();
        for(Predict mp : matchPredictList){
            String nickname = userRepo.findById(mp.getUser().getId()).get().getNickname();

            String predict = mp.getMatchResult().toString();
            String _predict = "HOME".equals(predict)?
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
    public MatchPredictVO getMyMatchPredict(String userEmail, long matchId) {
        Predict matchPredict = predictRepo.findByMatches_IdAndUser_Email(matchId,userEmail);

        MatchPredictVO result = MatchPredictVO.builder()
                .matchId(matchId)
                .whereWin(String.valueOf(matchPredict.getMatchResult()))
                .build();
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

    public TeamBoardVO getTeamInfo(int teamId) {
        TeamBoardVO result = new TeamBoardVO();
        Team team = teamRepo.findById(teamId).get();
        result =  new TeamBoardVO(team.getId(), team.getName(), team.getImage());
        return result;
    }

    public List<MatchBoardVO> getMatchBoards(int roundId) {
        List<MatchBoardVO> result = new ArrayList<>();
        List<Matches> matches = matchesRepo.findAllByRound(roundId);
        for(Matches match : matches){
            String name = "[" +match.getRound()+"R] "+match.getHome().getName()+ " VS " + match.getAway().getName();
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
        JSONObject jsonObject= new JSONObject();

        String str = "https://apiv3.apifootball.com/?action=get_events&match_id="+matchId+"&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        jsonObject = (JSONObject) ((JSONArray) JSONValue.parseWithException(isr)).get(0);
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
            playerMatch.setExpertRate(Integer.parseInt(playerStat.get("player_rating").toString()));
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
        List<MatchDet> matchDetList = matchDetRepo.findAllByMatches_Id(savedMatches.getId());
        for(int t=0;t<=1;t++){
            MatchDet savedMatchDet = matchDetList.get(t);
            if(savedMatchDet.getTeamType().equals(TeamType.HOME)){
                savedMatchDet.setFoul(foul[0][0]);
                savedMatchDet.setPass(pass[0][0]);
                savedMatchDet.setCorner(corner[0][0]);
                savedMatchDet.setOffside(offside[0][0]);
                savedMatchDet.setPenalty(penalty[0][0]);
                savedMatchDet.setPassOn(suc[0][0]);
                savedMatchDet.setPossession(poss[0][0]);
                savedMatchDet.setRed(red[0][0]);
                savedMatchDet.setYellow(yellow[0][0]);
                savedMatchDet.setShot(shot[0][0]);
                savedMatchDet.setShotOn(shotOn[0][0]);
                savedMatchDet.setSave(save[0][0]);
            }
            else{
                savedMatchDet.setFoul(foul[0][1]);
                savedMatchDet.setPass(pass[0][1]);
                savedMatchDet.setCorner(corner[0][1]);
                savedMatchDet.setOffside(offside[0][1]);
                savedMatchDet.setPenalty(penalty[0][1]);
                savedMatchDet.setPassOn(suc[0][1]);
                savedMatchDet.setPossession(poss[0][1]);
                savedMatchDet.setRed(red[0][1]);
                savedMatchDet.setYellow(yellow[0][1]);
                savedMatchDet.setShot(shot[0][1]);
                savedMatchDet.setShotOn(shotOn[0][1]);
                savedMatchDet.setSave(save[0][1]);
            }
            matchDetRepo.save(savedMatchDet);
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


    public List<MatchHistoryVO> getMatchHistory(long matchId) {
        List<MatchHistoryVO> result = new ArrayList<>();
        List<MatchHistory> histories = matchHistoryRepo.findAllByMatches_IdOrderByTime(matchId);

        for(MatchHistory history: histories){
            MatchHistoryVO vo = MatchHistoryVO.builder()
                    .time(history.getTime())
                    .history(history.getHistory().toString())
                    .teamType(history.getTeamType().toString())
                    .mainName(history.getMainName())
                    .subName(history.getSubName())
                    .info(history.getInfo())
                    .build();
            result.add(vo);
        }
        return result;
    }


    public int getMatchRound(long matchId) {
        Matches matches = matchesRepo.findById(matchId).get();
        return matches.getRound();
    }
}
