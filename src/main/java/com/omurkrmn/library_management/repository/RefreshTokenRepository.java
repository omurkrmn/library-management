package com.omurkrmn.library_management.repository;

import com.omurkrmn.library_management.entity.RefreshToken;
import com.omurkrmn.library_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByRefreshToken(String token);

    void deleteByUser(User user);
}
