package com.ejercicio.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryServiceTest {
    private LibraryService library;
    private Book book;

    @BeforeEach
    void setUp() {
        library = new LibraryService();
        book = new Book("ISBN-1", "El jardín", new Author("Ana", "Pérez"), Genre.MYSTERY, 2020);
        library.addBook(book);
        library.registerMember(new Member("L001", "Luis Soto"));
    }

    @Test
    void borrowingMarksBookUnavailableAndSetsDueDate() {
        Loan loan = library.borrowBook("ISBN-1", "L001", LocalDate.of(2026, 1, 1));

        assertFalse(book.isAvailable());
        assertEquals(LocalDate.of(2026, 1, 15), loan.dueDate());
        assertEquals(1, library.activeLoans().size());
    }

    @Test
    void aBookCannotBeBorrowedTwiceAtOnce() {
        library.borrowBook("ISBN-1", "L001", LocalDate.now());

        assertThrows(IllegalStateException.class,
                () -> library.borrowBook("ISBN-1", "L001", LocalDate.now()));
    }

    @Test
    void returningBookClosesLoanAndMakesBookAvailable() {
        library.borrowBook("ISBN-1", "L001", LocalDate.of(2026, 1, 1));

        Loan returnedLoan = library.returnBook("ISBN-1", "L001", LocalDate.of(2026, 1, 10));

        assertTrue(book.isAvailable());
        assertFalse(returnedLoan.isActive());
        assertEquals(LocalDate.of(2026, 1, 10), returnedLoan.returnedDate());
    }

    @Test
    void memberCannotExceedThreeActiveLoans() {
        library.addBook(new Book("ISBN-2", "Libro dos", new Author("Eva", "Luz"), Genre.CLASSIC, 2019));
        library.addBook(new Book("ISBN-3", "Libro tres", new Author("Eva", "Luz"), Genre.CLASSIC, 2018));
        library.addBook(new Book("ISBN-4", "Libro cuatro", new Author("Eva", "Luz"), Genre.CLASSIC, 2017));
        library.borrowBook("ISBN-1", "L001", LocalDate.now());
        library.borrowBook("ISBN-2", "L001", LocalDate.now());
        library.borrowBook("ISBN-3", "L001", LocalDate.now());

        assertThrows(IllegalStateException.class,
                () -> library.borrowBook("ISBN-4", "L001", LocalDate.now()));
    }
}