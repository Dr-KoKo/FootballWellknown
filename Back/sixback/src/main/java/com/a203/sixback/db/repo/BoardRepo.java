package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
    public Page<Board> findAll(Pageable pageable);

    List<Board> findAllByMatchId(Long matchId);

    List<Board> findAllByUser(User user);
}

