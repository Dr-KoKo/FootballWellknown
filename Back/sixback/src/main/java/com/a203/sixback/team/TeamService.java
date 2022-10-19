package com.a203.sixback.team;

import com.a203.sixback.db.entity.Coach;
import com.a203.sixback.db.entity.Player;
import com.a203.sixback.db.entity.Team;
import com.a203.sixback.db.repo.CoachRepo;
import com.a203.sixback.db.repo.PlayerRepo;
import com.a203.sixback.db.repo.TeamRepo;
import com.a203.sixback.team.vo.PlayerVO;
import com.a203.sixback.team.vo.TeamDet;
import com.a203.sixback.team.vo.TeamInfo;
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
                    team.getName(), team.getImage(), team.getWin(), team.getDraw(),
                    team.getLose(), team.getGoals(), team.getLoseGoals(), i, team.getWin()*3+ team.getDraw()));
            i++;
        }
        return result;
    }

    public TeamDet getTeamPlayers(int teamId) {
        TeamDet result = new TeamDet();
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
}
