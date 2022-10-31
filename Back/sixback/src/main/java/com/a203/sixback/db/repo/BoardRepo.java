package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
    public Page<Board> findAll(Pageable pageable);

    List<Board> findAllByMatchId(Pageable pageable, Long matchId);
    List<Board> findAllByTeamId(Pageable pageable, Long teamId);

//    @Query(value = "select b from Board b left join fetch c.wedulStudentList")
//    List<Board> findAllByUser(User user);

    @Query("SELECT b FROM Board b join fetch b.match m WHERE b.user = :author")

    List<Board> findAllByUser(@Param("author") User user);

}

