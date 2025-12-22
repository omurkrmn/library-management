package com.omurkrmn.library_management.dto.response;

import com.omurkrmn.library_management.entity.Role;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private UUID id;
    private String username;
    private Role role;

}
