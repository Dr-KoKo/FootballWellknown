package com.a203.sixback.match;

import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.repo.MatchesRepo;
import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.team.vo.MatchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchesRepo matchesRepo;
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
}
