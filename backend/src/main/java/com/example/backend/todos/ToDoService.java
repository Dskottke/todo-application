package com.example.backend.todos;

import com.example.backend.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                Status.OPEN,
                LocalDate.now(),
                newToDo.dueDate()
        ));
    }

    private boolean checkFieldsAreEmpty(NewToDo newToDo) {
        return newToDo.title().isEmpty() && newToDo.description().isEmpty();
    }

    public ToDo updateStatusById(String id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));

        getNextStatus(toDo);

        return toDoRepository.save(new ToDo(
                toDo.id(),
                toDo.description(),
                toDo.title(),
                getNextStatus(toDo),
                toDo.creationDate(),
                toDo.dueDate()
        ));
    }

    private Status getNextStatus(ToDo toDo) {
        if (toDo.status().equals(Status.OPEN)) {
            return Status.IN_PROGRESS;
        } else if (toDo.status().equals(Status.IN_PROGRESS)) {
            return Status.DONE;
        }
        throw new IllegalStateException("invalid status");
    }

    public void deleteToDoById(String id) {
        if (!toDoRepository.existsById(id)) {
            throw new ToDoNotFoundException(id);
        }
        toDoRepository.deleteById(id);
    }
}
