package com.example.backend;

import com.example.backend.model.NewToDo;
import com.example.backend.model.Status;
import com.example.backend.model.ToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository toDoRepository;
    private final Utils utils;

    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public ToDo addToDo(NewToDo newToDo) {

        if (checkFieldsAreEmpty(newToDo)) {
            throw new IllegalArgumentException("Title and description cannot be empty");
        }

        return toDoRepository.save(
                new ToDo(
                        utils.getUUID(),
                        newToDo.description(),
                        newToDo.title(),
                        Status.OPEN
                ));
    }

    private boolean checkFieldsAreEmpty(NewToDo newToDo) {
        return newToDo.title().isEmpty() && newToDo.description().isEmpty();
    }
}
