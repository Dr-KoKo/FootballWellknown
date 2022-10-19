package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.MatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchHistoryRepo extends JpaRepository<MatchHistory, Long> {
}
