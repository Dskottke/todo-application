package com.example.backend.exception;

public class ToDoNotFoundException extends RuntimeException {
    public ToDoNotFoundException(String id) {
        super("Could not find todo " + id);
    }
}
