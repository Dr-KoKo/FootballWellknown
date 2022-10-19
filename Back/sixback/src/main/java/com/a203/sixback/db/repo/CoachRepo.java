package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepo extends JpaRepository<Coach, Integer> {

    Coach findOneByTeam_Id(int teamId);
}
