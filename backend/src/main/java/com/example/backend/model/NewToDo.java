package com.example.backend.model;


import lombok.NonNull;

public record NewToDo(@NonNull String description,@NonNull String title) {
}
