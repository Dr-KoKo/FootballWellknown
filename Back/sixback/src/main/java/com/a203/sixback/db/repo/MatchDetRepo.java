package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.MatchDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDetRepo extends JpaRepository<MatchDet, Long> {
    List<MatchDet> findAllByMatches_Id(long matchId);
}
