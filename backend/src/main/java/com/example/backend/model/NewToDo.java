package com.example.backend.model;


import lombok.NonNull;

import java.time.LocalDate;

public record NewToDo(@NonNull String description, @NonNull String title, @NonNull LocalDate dueDate) {
}
