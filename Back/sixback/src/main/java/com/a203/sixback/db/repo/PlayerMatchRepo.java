package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.PlayerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerMatchRepo extends JpaRepository<PlayerMatch, Long> {
    List<PlayerMatch> findAllByMatches_Id(long matchId);

    PlayerMatch findByMatches_IdAndPlayer_Id(long matchId, long id);
}
