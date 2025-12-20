package com.omurkrmn.library_management.repository;

import com.omurkrmn.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
