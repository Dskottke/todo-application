package com.example.backend.model;

import java.time.LocalDate;

public record ToDo(String id, String description, String title, Status status, LocalDate creationDate) {
}
