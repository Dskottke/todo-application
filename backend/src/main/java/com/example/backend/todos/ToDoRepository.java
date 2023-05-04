package com.example.backend.todos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends MongoRepository<ToDo, String> {
    List<ToDo> findAllByUsername(String username);
}
