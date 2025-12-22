package com.omurkrmn.library_management.service;

import com.omurkrmn.library_management.dto.request.user.LoginRequest;
import com.omurkrmn.library_management.dto.response.AuthResponse;
import com.omurkrmn.library_management.entity.RefreshToken;
import com.omurkrmn.library_management.entity.User;
import com.omurkrmn.library_management.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AuthService(JwtUtil jwtUtil, RefreshTokenService refreshTokenService, UserService userService, ModelMapper modelMapper) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest);

        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }



    public void logout(String autHeader) {

        if(autHeader == null || !autHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization Header");
        }

        String token = autHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        User user = userService.getByUsername(username);

        refreshTokenService.deleteByUser(user);
    }
}
