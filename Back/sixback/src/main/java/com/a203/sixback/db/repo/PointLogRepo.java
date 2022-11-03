package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.PointLog;
import com.a203.sixback.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointLogRepo extends JpaRepository<PointLog, Long> {

    List<PointLog> findAllByUser(User user);

}
