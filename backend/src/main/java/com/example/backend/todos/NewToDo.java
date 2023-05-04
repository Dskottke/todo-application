package com.example.backend.todos;


import lombok.NonNull;

import java.time.LocalDate;

public record NewToDo(@NonNull String description, @NonNull String title, @NonNull LocalDate dueDate) {
}
