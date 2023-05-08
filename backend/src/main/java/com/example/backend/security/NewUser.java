package com.example.backend.security;

import lombok.NonNull;

public record NewUser(@NonNull String username, @NonNull String password) {
}
