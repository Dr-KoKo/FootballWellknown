package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Player;
import com.a203.sixback.db.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepo extends JpaRepository<Player, Long> {
    List<Player> findAllByTeam_Id(int teamId);
    List<Player> findTop10ByOrderByAssistsDesc();
    List<Player> findTop10ByOrderByGoalsDesc();
}
