package com.example.backend;

import com.example.backend.model.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoRepository extends MongoRepository<ToDo,String> {
}
