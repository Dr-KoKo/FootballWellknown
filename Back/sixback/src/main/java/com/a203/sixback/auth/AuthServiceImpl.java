package com.a203.sixback.auth;

import com.a203.sixback.db.repo.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private final UserRefreshTokenRepository userRefreshTokenRepository;


    @Override
    public void signOut(String refreshToken) {
        userRefreshTokenRepository.deleteUserRefreshTokenByRefreshToken(refreshToken);
    }
}
