package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.MatchPredict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchPredictRepo extends JpaRepository<MatchPredict, Long> {

    List<MatchPredict> findAllByMatches_Id(Long matchId);
}
