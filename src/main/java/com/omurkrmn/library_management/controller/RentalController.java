package com.omurkrmn.library_management.controller;

import com.omurkrmn.library_management.dto.request.bookRental.BookRentalRequest;
import com.omurkrmn.library_management.dto.response.RentalResponse;
import com.omurkrmn.library_management.service.RentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rentals")
@Slf4j
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<RentalResponse> rent(@RequestBody BookRentalRequest bookRentalRequest){
        log.info("Rent request received");
        return ResponseEntity.ok(rentalService.createRental(bookRentalRequest));
    }

    @PostMapping("/return/{rentalId}")
    public ResponseEntity<RentalResponse> returnBook(@PathVariable UUID rentalId){
        log.info("Return request received");
        return ResponseEntity.ok(rentalService.returnBook(rentalId));
    }
}
