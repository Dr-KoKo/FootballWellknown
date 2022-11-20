package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepoMySQL extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long boardId);
}
