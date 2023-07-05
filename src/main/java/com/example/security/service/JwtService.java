package com.example.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "c342dd5ff691b05ce02f7d0dd292a65eb4d89965";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
    public String generateToken(User user, List<GrantedAuthority> authorities) {
        Instant expirationTime = Instant.now().plusSeconds(86400);
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(Date.from(expirationTime)) // jwt valid for 1 day
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, List<GrantedAuthority> authorities) {
        Instant expirationTime = Instant.now().plusSeconds(86400);
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(Date.from(expirationTime)) // jwt valid for 1 day
                .sign(algorithm);
    }
}
