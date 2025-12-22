package com.omurkrmn.library_management.dto.response;

public record AuthResponse(

        String accessToken,
        String refreshToken
) {
}
