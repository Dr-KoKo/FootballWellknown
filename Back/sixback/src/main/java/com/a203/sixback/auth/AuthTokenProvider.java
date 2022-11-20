package com.a203.sixback.auth;

import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.redis.UserCacheRepository;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {

    private final UserRepo userRepo;
    private final UserCacheRepository userCacheRepository;
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret, UserRepo userRepo, UserCacheRepository userCacheRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userRepo = userRepo;
        this.userCacheRepository = userCacheRepository;
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());
//            User principal = new User(claims.getSubject(), "", authorities);

            System.out.println(claims.getSubject());

            User user = userCacheRepository.getUser(claims.getSubject()).orElseGet(
                    ()->userRepo.findByEmail(claims.getSubject())
            );
//
            UserPrincipal principal = UserPrincipal.create(user);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}
