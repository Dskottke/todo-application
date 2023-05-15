package com.example.backend.security.models;
import com.example.backend.security.Role;

public record AppUser(String id, String username, String password, Role role, boolean confirmed) {
}
