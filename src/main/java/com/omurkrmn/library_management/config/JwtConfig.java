package com.omurkrmn.library_management.config;


import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.expiration}")
    private long expiration;

    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
