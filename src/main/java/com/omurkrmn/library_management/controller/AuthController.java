package com.omurkrmn.library_management.controller;

import com.omurkrmn.library_management.dto.request.user.LoginRequest;
import com.omurkrmn.library_management.dto.request.user.RegisterRequest;
import com.omurkrmn.library_management.dto.response.UserResponse;
import com.omurkrmn.library_management.security.JwtUtil;
import com.omurkrmn.library_management.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {

        log.info("Register request received");
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        UserResponse user = userService.login(request);
        String token = jwtUtil.generateToken(user.getUsername());

        log.info("Login request received");
        return ResponseEntity.ok(token);
    }
}
