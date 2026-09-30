package com.ejercicio.biblioteca;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final LibraryService library = new LibraryService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        seedData();
        System.out.println("=== Biblioteca Horizonte ===");

        boolean running = true;
        while (running) {
            showMenu();
            switch (readOption()) {
                case 1 -> showBooks(library.books());
                case 2 -> searchBooks();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> showActiveLoans();
                case 0 -> running = false;
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        }
        System.out.println("Hasta pronto.");
    }

    private void seedData() {
        library.addBook(new Book("978-84-376-0494-7", "Cien años de soledad",
                new Author("Gabriel García", "Márquez"), Genre.CLASSIC, 1967));
        library.addBook(new Book("978-84-450-0305-5", "Dune",
                new Author("Frank", "Herbert"), Genre.SCIENCE_FICTION, 1965));
        library.addBook(new Book("978-84-9838-708-5", "El nombre del viento",
                new Author("Patrick", "Rothfuss"), Genre.FANTASY, 2007));
        library.addBook(new Book("978-84-233-4983-8", "La sombra del viento",
                new Author("Carlos Ruiz", "Zafón"), Genre.MYSTERY, 2001));
        library.addBook(new Book("978-84-670-4183-9", "Sapiens",
                new Author("Yuval Noah", "Harari"), Genre.HISTORY, 2011));
        library.registerMember(new Member("L001", "Lucía Fernández"));
        library.registerMember(new Member("L002", "Mateo García"));
        library.registerMember(new Member("L003", "Sofía Martín"));
    }

    private void showMenu() {
        System.out.println("1. Ver catálogo");
        System.out.println("2. Buscar libros");
        System.out.println("3. Prestar un libro");
        System.out.println("4. Devolver un libro");
        System.out.println("5. Ver préstamos activos");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private int readOption() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private void showBooks(java.util.List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }
        books.forEach(book -> System.out.printf("%s | %s | %s | %s | %d | %s%n",
                book.isbn(), book.title(), book.author().fullName(), book.genre().displayName(),
                book.publicationYear(), book.isAvailable() ? "Disponible" : "Prestado"));
    }

    private void searchBooks() {
        System.out.print("Buscar por título, autor o género: ");
        showBooks(library.searchBooks(scanner.nextLine()));
    }

    private void borrowBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        showMembers();
        System.out.print("ID del lector: ");
        String memberId = scanner.nextLine().trim();
        try {
            Loan loan = library.borrowBook(isbn, memberId, LocalDate.now());
            System.out.printf("Préstamo registrado. Devolver antes del %s.%n", loan.dueDate());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void returnBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("ID del lector: ");
        String memberId = scanner.nextLine().trim();
        try {
            library.returnBook(isbn, memberId, LocalDate.now());
            System.out.println("Devolución registrada.");
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void showMembers() {
        System.out.println("Lectores: " + library.members().stream()
                .map(member -> member.id() + " - " + member.name())
                .collect(java.util.stream.Collectors.joining(" | ")));
    }

    private void showActiveLoans() {
        if (library.activeLoans().isEmpty()) {
            System.out.println("No hay préstamos activos.");
            return;
        }
        library.activeLoans().forEach(loan -> System.out.printf("%s | %s | %s | Vence: %s%n",
                loan.book().title(), loan.member().name(), loan.member().id(), loan.dueDate()));
    }
}