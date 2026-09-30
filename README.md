# Biblioteca Horizonte

Aplicación de consola en Java para gestionar un catálogo de libros y sus préstamos. Incluye autores, géneros, lectores, fechas de vencimiento, búsqueda y validaciones para evitar préstamos inválidos.

## Requisitos

- Java 21 o superior
- Maven 3.9 o superior

## Ejecutar

```bash
mvn test
mvn package
java -jar target/biblioteca-console-1.0.0.jar
```

El catálogo y los lectores de ejemplo se cargan al iniciar. Los datos viven en memoria y se reinician al cerrar la aplicación.

## Reglas del préstamo

- Cada préstamo dura 14 días.
- Un libro prestado no puede volver a prestarse hasta su devolución.
- Cada lector puede tener hasta 3 préstamos activos.
- La búsqueda acepta título, autor o género, sin distinguir mayúsculas.

## Estructura

- `Book`, `Author`, `Member` y `Loan`: entidades del dominio.
- `Genre`: géneros disponibles.
- `LibraryService`: reglas del catálogo y de los préstamos.
- `Main`: menú interactivo de consola.
- `LibraryServiceTest`: pruebas de las reglas principales.