package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Team, Integer> {
    Team findByName(String name);
}
