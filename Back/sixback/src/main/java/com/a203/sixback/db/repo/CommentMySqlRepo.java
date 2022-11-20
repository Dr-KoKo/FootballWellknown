package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.CommentMySql;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentMySqlRepo extends JpaRepository<CommentMySql, Long> {
    List<CommentMySql> findAllByBoardId(long boardId);
}
