package com.example.backend.security.models;

import lombok.NonNull;

public record NewUser(@NonNull String username, @NonNull String password) {
}
