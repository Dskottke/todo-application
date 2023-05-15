package com.example.backend.security.models;

import com.example.backend.security.Role;

public record ConfirmUser(String id, String username, Role role) {
}
