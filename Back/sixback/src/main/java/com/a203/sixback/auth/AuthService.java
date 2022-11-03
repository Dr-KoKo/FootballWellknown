package com.a203.sixback.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    void signOut(String refreshToken);
}
