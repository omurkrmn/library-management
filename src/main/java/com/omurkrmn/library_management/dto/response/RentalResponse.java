package com.omurkrmn.library_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RentalResponse {

    private UUID rentalId;
    private String username;
    private String bookTitle;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;
}
