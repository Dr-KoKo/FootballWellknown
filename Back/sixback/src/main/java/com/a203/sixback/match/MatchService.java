package com.a203.sixback.match;

import com.a203.sixback.db.entity.MatchDet;
import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.entity.PlayerMatch;
import com.a203.sixback.db.repo.MatchDetRepo;
import com.a203.sixback.db.repo.MatchesRepo;
import com.a203.sixback.db.repo.PlayerMatchRepo;
import com.a203.sixback.match.vo.LineUp;
import com.a203.sixback.match.vo.LineUpVO;
import com.a203.sixback.match.vo.MatchStatusVO;
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
        List<Matches> matches = matchesRepo.findAllByYearAndMonth(year,month,day);
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

}
