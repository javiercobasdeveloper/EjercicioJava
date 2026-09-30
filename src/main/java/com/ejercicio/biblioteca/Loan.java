package com.ejercicio.biblioteca;

import java.time.LocalDate;

public class Loan {
    private final Book book;
    private final Member member;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnedDate;

    Loan(Book book, Member member, LocalDate loanDate, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public Book book() {
        return book;
    }

    public Member member() {
        return member;
    }

    public LocalDate loanDate() {
        return loanDate;
    }

    public LocalDate dueDate() {
        return dueDate;
    }

    public LocalDate returnedDate() {
        return returnedDate;
    }

    public boolean isActive() {
        return returnedDate == null;
    }

    void markReturned(LocalDate date) {
        returnedDate = date;
    }
}