package com.example.backend;

import com.example.backend.model.NewToDo;
import com.example.backend.model.ToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping()
    public List<ToDo> getAllTodos() {
        return toDoService.getAllToDos();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo addToDo(@Validated @RequestBody NewToDo newToDo) {
        return toDoService.addToDo(newToDo);
    }

    @PutMapping("{id}")
    public ToDo updateToDo(@PathVariable String id) {
        return toDoService.updateStatusById(id);
    }
}
