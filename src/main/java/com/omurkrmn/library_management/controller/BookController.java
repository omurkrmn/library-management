package com.omurkrmn.library_management.controller;

import com.omurkrmn.library_management.dto.request.book.BookCreateRequest;
import com.omurkrmn.library_management.dto.response.BookResponse;
import com.omurkrmn.library_management.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        log.info("Book Create Request: {}", bookCreateRequest);
        return ResponseEntity.ok(bookService.create(bookCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {

        log.info("Get All Books request");
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
