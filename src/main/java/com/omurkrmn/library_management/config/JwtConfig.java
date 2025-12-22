package com.omurkrmn.library_management.config;


import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {


    private String secret;

    private long expiration;

    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
