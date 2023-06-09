package com.example.backend.todos;

import java.time.LocalDate;

public record ToDo(
        String id,
        String username,
        String description,
        String title,
        Status status,
        LocalDate creationDate ,
        LocalDate dueDate) {
}
