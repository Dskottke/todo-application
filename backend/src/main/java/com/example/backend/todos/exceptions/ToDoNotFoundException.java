package com.example.backend.todos.exceptions;

public class ToDoNotFoundException extends RuntimeException {
    public ToDoNotFoundException(String id) {
        super("Could not find todo " + id);
    }
}
