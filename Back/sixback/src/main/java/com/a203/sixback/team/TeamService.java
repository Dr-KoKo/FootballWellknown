package com.a203.sixback.team;

import com.a203.sixback.db.entity.Team;
import com.a203.sixback.db.repo.TeamRepo;
import com.a203.sixback.team.vo.TeamInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepo teamRepo;
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
}
