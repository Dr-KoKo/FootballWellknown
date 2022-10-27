package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictRepo extends JpaRepository<Predict, Long> {
    List<Predict> findAllByUser(User user);
}
