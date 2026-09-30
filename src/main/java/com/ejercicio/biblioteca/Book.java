package com.ejercicio.biblioteca;

public class Book {
    private final String isbn;
    private final String title;
    private final Author author;
    private final Genre genre;
    private final int publicationYear;
    private boolean available = true;

    public Book(String isbn, String title, Author author, Genre genre, int publicationYear) {
        if (isbn == null || isbn.isBlank() || title == null || title.isBlank()) {
            throw new IllegalArgumentException("El ISBN y el título son obligatorios.");
        }
        if (author == null || genre == null) {
            throw new IllegalArgumentException("El autor y el género son obligatorios.");
        }
        if (publicationYear < 1) {
            throw new IllegalArgumentException("El año de publicación no es válido.");
        }
        this.isbn = isbn.trim();
        this.title = title.trim();
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public String isbn() {
        return isbn;
    }

    public String title() {
        return title;
    }

    public Author author() {
        return author;
    }

    public Genre genre() {
        return genre;
    }

    public int publicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return available;
    }

    void markBorrowed() {
        available = false;
    }

    void markReturned() {
        available = true;
    }
}