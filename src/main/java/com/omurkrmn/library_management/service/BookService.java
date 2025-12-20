package com.omurkrmn.library_management.service;

import com.omurkrmn.library_management.dto.request.book.BookCreateRequest;
import com.omurkrmn.library_management.dto.response.BookResponse;
import com.omurkrmn.library_management.entity.Book;
import com.omurkrmn.library_management.exception.BusinessException;
import com.omurkrmn.library_management.messasing.event.LibraryEvent;
import com.omurkrmn.library_management.messasing.producer.LibraryEventProducer;
import com.omurkrmn.library_management.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final LibraryEventProducer libraryEventProducer;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper, LibraryEventProducer libraryEventProducer) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.libraryEventProducer = libraryEventProducer;
    }

    public BookResponse create(BookCreateRequest bookCreateRequest) {

        Book book = modelMapper.map(bookCreateRequest, Book.class);
        Book savedBook = bookRepository.save(book);

        log.info("Book created: {}", savedBook.getTitle());

        libraryEventProducer.sendMessage(
                new LibraryEvent(
                        "BOOK_CREATED",
                        savedBook.getTitle(),
                        LocalDateTime.now()
                )
        );
        return modelMapper.map(savedBook, BookResponse.class);
    }

    public List<BookResponse>   getAllBooks() {
        log.info("Get all books");
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new BusinessException("Book not found"));
    }

    public void decreaseStock(Book book) {
        if(book.getStock()<=0) {
            throw new BusinessException("Book out of stock");
        }
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
    }

    public void increaseStock(Book book) {
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);
    }
}
