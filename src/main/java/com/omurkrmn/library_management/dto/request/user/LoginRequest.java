package com.omurkrmn.library_management.dto.request.user;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
