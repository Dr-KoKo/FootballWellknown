package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.PlayerEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerEvaluateRepo extends JpaRepository<PlayerEvaluate, Long> {
    PlayerEvaluate findById(long id);
}
