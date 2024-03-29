package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByEmail(String email);
    UserRefreshToken findByEmailAndRefreshToken(String email, String refreshToken);
    @Transactional
    Integer deleteUserRefreshTokenByRefreshToken(String refreshToken);

    UserRefreshToken findOneByRefreshToken(String refreshToken);
}