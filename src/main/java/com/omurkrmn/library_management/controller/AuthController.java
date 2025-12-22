package com.omurkrmn.library_management.controller;

import com.omurkrmn.library_management.dto.request.user.LoginRequest;
import com.omurkrmn.library_management.dto.request.user.RegisterRequest;
import com.omurkrmn.library_management.dto.response.AuthResponse;
import com.omurkrmn.library_management.dto.response.UserResponse;
import com.omurkrmn.library_management.entity.RefreshToken;
import com.omurkrmn.library_management.entity.User;
import com.omurkrmn.library_management.security.JwtUtil;
import com.omurkrmn.library_management.service.AuthService;
import com.omurkrmn.library_management.service.RefreshTokenService;
import com.omurkrmn.library_management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, RefreshTokenService refreshTokenService, AuthService authService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {

        log.info("Register request received");
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        User user = userService.authenticate(request);
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        log.info("Login request received");
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken) {
        RefreshToken token = refreshTokenService.verifyExpiration(
                refreshTokenService.findByRefreshToken(refreshToken));

        User user = token.getUser();

        String newAccessToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
        log.info("Refresh token received");
        return ResponseEntity.ok(
                new AuthResponse(
                        newAccessToken,
                        token.getToken()));

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization")String header) {

        authService.logout(header);
        return ResponseEntity.noContent().build();
    }

}
