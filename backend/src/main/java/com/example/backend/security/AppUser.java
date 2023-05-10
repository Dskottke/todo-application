package com.example.backend.security;


public record AppUser(String id, String username, String password, Role role, boolean confirmed) {
}
