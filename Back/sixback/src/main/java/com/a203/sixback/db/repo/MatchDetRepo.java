package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.MatchDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDetRepo extends JpaRepository<MatchDet, Long> {
}
