package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchesRepo extends JpaRepository<Matches, Long> {
}
