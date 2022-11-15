package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.CommentMySql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentMySqlRepo extends JpaRepository<CommentMySql, Long> {
}
