package com.example.backend.todos;

public class ToDoNotFoundException extends RuntimeException {
    public ToDoNotFoundException(String id) {
        super("Could not find todo " + id);
    }
}
