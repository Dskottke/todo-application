package com.example.backend.security.exceptions;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String message) {
        super(message);
    }
}
