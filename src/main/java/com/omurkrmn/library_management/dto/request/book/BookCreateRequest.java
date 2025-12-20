package com.omurkrmn.library_management.dto.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookCreateRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String author;

    @Min(1)
    private int stock;
}
