package com.omurkrmn.library_management.service;

import com.omurkrmn.library_management.dto.request.bookRental.BookRentalRequest;
import com.omurkrmn.library_management.dto.response.RentalResponse;
import com.omurkrmn.library_management.entity.Book;
import com.omurkrmn.library_management.entity.Rental;
import com.omurkrmn.library_management.entity.User;
import com.omurkrmn.library_management.exception.BusinessException;
import com.omurkrmn.library_management.messasing.event.LibraryEvent;
import com.omurkrmn.library_management.messasing.producer.LibraryEventProducer;
import com.omurkrmn.library_management.repository.RentalRepository;
import com.omurkrmn.library_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookService bookService;
    private final LibraryEventProducer libraryEventProducer;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, BookService bookService, LibraryEventProducer libraryEventProducer) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
        this.libraryEventProducer = libraryEventProducer;
    }

    @Transactional
    public RentalResponse createRental(BookRentalRequest bookRentalRequest) {

        User user = userRepository.findById(bookRentalRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookService.getBookById(bookRentalRequest.getBookId());

        rentalRepository.findByUserIdAndBookIdAndReturnedFalse(user.getId(), book.getId())
                .ifPresent(rental -> {
                    throw new RuntimeException("Book already rented by this user");
                });

        bookService.decreaseStock(book);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentDate(LocalDate.now());
        rental.setReturned(false);

        Rental savedRental = rentalRepository.save(rental);

        log.info("Book rented: {} by {}", book.getTitle(), user.getUsername());

        libraryEventProducer.sendMessage(
                new LibraryEvent(
                        "BOOK_RENTED",
                        user.getUsername() + " rented " + book.getTitle(),
                        LocalDateTime.now()
                )
        );

        return new RentalResponse(
                savedRental.getId(),
                user.getUsername(),
                book.getTitle(),
                savedRental.getRentDate(),
                null,
                false
        );
    }

    @Transactional
    public RentalResponse returnBook(UUID rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Rental not found"));

        if (rental.isReturned()) {
            throw new BusinessException("Book already returned");
        }

        rental.setReturned(true);
        rental.setReturnDate(LocalDate.now());

        bookService.increaseStock(rental.getBook());

        Rental savedRental = rentalRepository.save(rental);

        log.info("Book returned: {} ", rental.getBook().getTitle());

        libraryEventProducer.sendMessage(
                new LibraryEvent(
                       "BOOK_RETURNED",
                       rental.getBook().getTitle(),
                       LocalDateTime.now()
                )
        );

        return new RentalResponse(
                savedRental.getId(),
                savedRental.getUser().getUsername(),
                savedRental.getBook().getTitle(),
                savedRental.getRentDate(),
                savedRental.getReturnDate(),
                true
        );
    }
}
