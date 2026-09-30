package com.ejercicio.biblioteca;

public record Author(String firstName, String lastName) {
    public Author {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El autor debe tener nombre y apellido.");
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}