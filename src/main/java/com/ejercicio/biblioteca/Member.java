package com.ejercicio.biblioteca;

public record Member(String id, String name) {
    public Member {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("El lector debe tener identificador y nombre.");
        }
        id = id.trim();
        name = name.trim();
    }
}