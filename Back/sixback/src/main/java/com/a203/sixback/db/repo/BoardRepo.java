package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
    public Page<Board> findAll(Pageable pageable);

    List<Board> findAllByMatchId(Pageable pageable, Long matchId);
    List<Board> findAllByTeamId(Pageable pageable, Long teamId);

//    @Query(value = "select b from Board b left join fetch c.wedulStudentList")
//    List<Board> findAllByUser(User user);

//    @Query("SELECT b FROM Board b join fetch b.match m WHERE b.user = :author")
//
//    List<Board> findAllByUser(@Param("author") User user);

    Page<Board> findAllByUser(User user, Pageable pageable);

    List<Board> findTop4ByTeamIdOrderByIdDesc(Integer teamId);

    List<Board> findTop4ByMatchIdOrderByIdDesc(Long matchId);

    List<Board> findByTitleContains(Pageable pageable, String keyword);

    List<Board> findByContentContains(Pageable pageable, String keyword);

    List<Board> findByContentOrTitleContains(Pageable pageable, String contentKeyWord, String titleKeyword);

    @Query("SELECT COUNT(*) FROM Board b WHERE b.title like %?1%")
    long countByTitleKeyword(String keyword);

    @Query("SELECT COUNT(*) FROM Board b WHERE b.content like %?1%")
    long countByContentKeyword(String keyword);

    @Query("SELECT COUNT(*) FROM Board b WHERE b.title like %?1% or b.content like %?1%")
    long countByContentOrTitleKeyword(String keyword);
}

