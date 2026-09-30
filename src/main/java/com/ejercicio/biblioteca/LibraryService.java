package com.ejercicio.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class LibraryService {
    private static final int MAX_ACTIVE_LOANS = 3;
    private static final int LOAN_DURATION_DAYS = 14;

    private final Map<String, Book> books = new TreeMap<>();
    private final Map<String, Member> members = new TreeMap<>();
    private final List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        if (books.putIfAbsent(book.isbn(), book) != null) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN.");
        }
    }

    public void registerMember(Member member) {
        if (members.putIfAbsent(member.id(), member) != null) {
            throw new IllegalArgumentException("Ya existe un lector con ese identificador.");
        }
    }

    public List<Book> books() {
        return List.copyOf(books.values());
    }

    public List<Book> searchBooks(String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        return books.values().stream()
                .filter(book -> book.title().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || book.author().fullName().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || book.genre().displayName().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .toList();
    }

    public List<Member> members() {
        return List.copyOf(members.values());
    }

    public List<Loan> loans() {
        return List.copyOf(loans);
    }

    public List<Loan> activeLoans() {
        return loans.stream().filter(Loan::isActive).toList();
    }

    public Loan borrowBook(String isbn, String memberId, LocalDate loanDate) {
        Book book = findBook(isbn).orElseThrow(() -> new IllegalArgumentException("No se encontró el libro."));
        Member member = findMember(memberId).orElseThrow(() -> new IllegalArgumentException("No se encontró el lector."));

        if (!book.isAvailable()) {
            throw new IllegalStateException("El libro no está disponible.");
        }
        long memberLoanCount = activeLoans().stream()
                .filter(loan -> loan.member().id().equals(member.id()))
                .count();
        if (memberLoanCount >= MAX_ACTIVE_LOANS) {
            throw new IllegalStateException("El lector ya alcanzó el límite de préstamos activos.");
        }

        Loan loan = new Loan(book, member, loanDate, loanDate.plusDays(LOAN_DURATION_DAYS));
        book.markBorrowed();
        loans.add(loan);
        return loan;
    }

    public Loan returnBook(String isbn, String memberId, LocalDate returnedDate) {
        Loan loan = loans.stream()
                .filter(Loan::isActive)
                .filter(candidate -> candidate.book().isbn().equals(isbn)
                        && candidate.member().id().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay un préstamo activo que coincida."));
        loan.markReturned(returnedDate);
        loan.book().markReturned();
        return loan;
    }

    public Optional<Book> findBook(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    private Optional<Member> findMember(String id) {
        return Optional.ofNullable(members.get(id));
    }
}