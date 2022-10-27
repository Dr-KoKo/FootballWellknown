package com.a203.sixback.team;

import com.a203.sixback.db.entity.Coach;
import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.entity.Player;
import com.a203.sixback.db.entity.Team;
import com.a203.sixback.db.repo.CoachRepo;
import com.a203.sixback.db.repo.MatchesRepo;
import com.a203.sixback.db.repo.PlayerRepo;
import com.a203.sixback.db.repo.TeamRepo;
import com.a203.sixback.team.vo.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepo teamRepo;
    private final ModelMapper modelMapper;
    private final CoachRepo coachRepo;
    private final PlayerRepo playerRepo;
    private final MatchesRepo matchesRepo;
    public ArrayList<TeamInfo> getTeamRanks() {
        ArrayList<TeamInfo> result = new ArrayList<>();
        List<Team> list = teamRepo.findAll();
        Collections.sort(list,new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int pts1 = o1.getWin() * 3 + o1.getDraw();
                int pts2 = o2.getWin() * 3 + o2.getDraw();
                if(pts1==pts2){
                    return (o2.getGoals()-o2.getLoseGoals()) - (o1.getGoals()-o1.getLoseGoals());
                }
                return pts2-pts1;
            }
        });
        int i = 1;
        for(Team team : list){
            result.add(new TeamInfo(
                    team.getId(),team.getName(), team.getImage(), team.getWin(), team.getDraw(),
                    team.getLose(), team.getGoals(), team.getLoseGoals(), i, team.getWin()*3+ team.getDraw()));
            i++;
        }
        return result;
    }

    public TeamPlayers getTeamPlayers(int teamId) {
        TeamPlayers result = new TeamPlayers();
        List<Player> list = playerRepo.findAllByTeam_Id(teamId);
        List<PlayerVO> players = list.stream().map(player -> modelMapper.map(player, PlayerVO.class)).collect(Collectors.toList());
        System.out.println(players);
        Coach coach = coachRepo.findOneByTeam_Id(teamId);
        result.setCoachAge(coach.getAge());
        result.setCoachImage(coach.getImage());
        result.setCoachName(coach.getName());
        result.setCountry(coach.getCountry());
        result.setPlayers(players);
        return result;
    }

    public TeamDetVO getTeamDetails(int teamId) {
        TeamDetVO result = new TeamDetVO();
        List<Team> list = teamRepo.findAll();
        Collections.sort(list,new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int pts1 = o1.getWin() * 3 + o1.getDraw();
                int pts2 = o2.getWin() * 3 + o2.getDraw();
                if(pts1==pts2){
                    return (o2.getGoals()-o2.getLoseGoals()) - (o1.getGoals()-o1.getLoseGoals());
                }
                return pts2-pts1;
            }
        });
        Team team = teamRepo.findById(teamId).get();
        int rank =0;
        for(int i=0;i<list.size();i++){
            if(team.getId()==list.get(i).getId()){
                rank = i+1;
            }
        }
        TeamInfo teamInfo = TeamInfo.builder()
                .draw(team.getDraw())
                .goals(team.getGoals())
                .image(team.getImage())
                .name(team.getName())
                .win(team.getWin())
                .lose(team.getLose())
                .rank(rank)
                .loseGoals(team.getLoseGoals())
                .pts(team.getWin()*3+team.getDraw())
                .build();
        List<MatchVO> finMatchVOs = new ArrayList<>();
        List<MatchVO> readyMatchVOs = new ArrayList<>();
        List<Matches> finMatches = matchesRepo.findRecentFINMatches(teamId);
        List<Matches> readyMatches = matchesRepo.findRecentREADYMatches(teamId);

        for(Matches match : finMatches){
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
            finMatchVOs.add(matchVO);
        }
        for(Matches match : readyMatches){
            MatchVO matchVO = MatchVO.builder()
                    .matchId(match.getId())
                    .home(match.getHome().getName())
                    .homeImage(match.getHome().getImage())
                    .away(match.getAway().getName())
                    .awayImage(match.getAway().getImage())
                    .homeScore(match.getHomeScore())
                    .awayScore(match.getAwayScore())
                    .date(match.getMatchDate().toString())
                    .stadium(match.getStadium())
                    .build();
            readyMatchVOs.add(matchVO);
        }
        result.setTeamInfo(teamInfo);
        result.setFinMatch(finMatchVOs);
        result.setYetMatch(readyMatchVOs);
        return result;
    }

    public PlayerDetVO getPlayerDetails(long playerId) {
        Player player = playerRepo.findById(playerId).get();
        PlayerDetVO result = PlayerDetVO.builder()
                .assists(player.getAssists())
                .birth(player.getBirth())
                .country(player.getCountry())
                .height(player.getHeight())
                .history(player.getHistory())
                .joinMatches(player.getJoinMatches())
                .number(player.getNumber())
                .position(player.getPosition())
                .weight(player.getWeight())
                .goals(player.getGoals())
                .image(player.getImage())
                .name(player.getName())
                .build();
        return result;
    }
}
