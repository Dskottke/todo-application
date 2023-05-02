package com.example.backend;

import com.example.backend.model.NewToDo;
import com.example.backend.model.ToDo;
import lombok.RequiredArgsConstructor;
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
    public ToDo addToDo(@RequestBody NewToDo newToDo) {
        return toDoService.addToDo(newToDo);
    }
}
