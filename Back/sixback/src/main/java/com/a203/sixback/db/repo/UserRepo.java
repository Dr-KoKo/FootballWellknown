package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
//    Optional<User> findByEmail(String email);
    boolean existsByNickname(String nickname);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update user set nickname=:nickname where email=:email")
    void updateNickname(@Param("nickname") String nickname, @Param("email") String email);
}
