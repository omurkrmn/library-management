package com.omurkrmn.library_management.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
