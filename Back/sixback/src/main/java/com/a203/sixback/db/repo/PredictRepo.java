package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictRepo extends JpaRepository<Predict, Long> {
    List<Predict> findAllByUser(User user);
}
