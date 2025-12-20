package com.omurkrmn.library_management.dto.request.bookRental;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BookRentalRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID bookId;
}
