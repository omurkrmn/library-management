package com.omurkrmn.library_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BookResponse {

    private UUID id;
    private String title;
    private String author;
    private int stock;
}
