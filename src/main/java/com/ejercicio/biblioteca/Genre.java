package com.ejercicio.biblioteca;

public enum Genre {
    FANTASY("Fantasía"),
    SCIENCE_FICTION("Ciencia ficción"),
    MYSTERY("Misterio"),
    HISTORY("Historia"),
    CLASSIC("Clásico"),
    OTHER("Otro");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}