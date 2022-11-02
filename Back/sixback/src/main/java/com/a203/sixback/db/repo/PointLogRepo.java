package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointLogRepo extends JpaRepository<PointLog, Long> {

}
