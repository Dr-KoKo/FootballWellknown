package com.a203.sixback.match;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.repo.*;
import com.a203.sixback.match.vo.*;
import com.a203.sixback.team.vo.MatchVO;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchesRepo matchesRepo;
    private final MatchDetRepo matchDetRepo;
    private final PlayerMatchRepo playerMatchRepo;
    private final PlayerEvaluateRepo playerEvaluateRepo;
    private final PlayerRepo playerRepo;
    private final UserRepo userRepo;
    private final MatchPredictRepo matchPredictRepo;
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
            playerEvaluateRepo.save(new PlayerEvaluate(matches,player,playerEvaluateVO.getScore()));
        }
    }


    public void matchPredict(MatchPredictVO matchPredictVO) {
        Matches matches = matchesRepo.findById(matchPredictVO.getMatchId()).get();
        User user = userRepo.findById(matchPredictVO.getUserId()).get();

        MatchPredict newMatchPredict = new MatchPredict(user, matches, matchPredictVO.getWhereWin());
        matchPredictRepo.save(newMatchPredict);
    }

    public List<MatchPredictVO> getAllMatchPredict(long matchId) {
        List<MatchPredict> matchPredictList = matchPredictRepo.findAllByMatches_Id(matchId);
        List<MatchPredictVO> result = new ArrayList<>();
        for(MatchPredict mp : matchPredictList){
            String nickname = userRepo.findById(mp.getUser().getId()).get().getNickname();
            MatchPredictVO vo = MatchPredictVO.builder()
                    .matchId(matchId)
                    .userNickname(nickname)
                    .whereWin(mp.getWhereWin())
                    .build();
            result.add(vo);
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
}
