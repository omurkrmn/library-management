package com.omurkrmn.library_management.repository;

import com.omurkrmn.library_management.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    Optional<Rental> findByUserIdAndBookIdAndReturnedFalse(UUID userId, UUID bookId);
}
