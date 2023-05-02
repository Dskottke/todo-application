package com.example.backend;

import com.example.backend.model.NewToDo;
import com.example.backend.model.Status;
import com.example.backend.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ToDoServiceTest {

    private final ToDoRepository toDoRepository = Mockito.mock(ToDoRepository.class);
    private final Utils utils = Mockito.mock(Utils.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository, utils);

    @Test
    @DisplayName("getAllToDos and expect an empty list")
    void getAllToDosExpectEmptyList() {
        //GIVEN
        List<ToDo> expected = Collections.emptyList();
        //WHEN
        when(toDoRepository.findAll()).thenReturn(expected);
        List<ToDo> actual = toDoService.getAllToDos();
        //THEN
        verify(toDoRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AddToDo with a newToDo DTO and expect the new ToDo as a return")
    void addToDoWithDtoAndExpectToDo() {
        //GIVEN
        NewToDo testDTO = new NewToDo("testDescription", "testTitle");
        //WHEN
        ToDo expected = new ToDo("testId", "testDescription", "testTitle", Status.OPEN);
        when(utils.getUUID()).thenReturn("testId");
        when(toDoRepository.save(expected)).thenReturn(expected);
        ToDo actual = toDoService.addToDo(testDTO);
        //THEN
        verify(utils).getUUID();
        verify(toDoRepository).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AddToDo with a newToDo DTO that has empty fields - should throw IllegalArgumentException")
    void addToDoWithDtoAndExpectException() {
        //GIVEN
        NewToDo testDTO = new NewToDo("", "");
        //WHEN

        //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    toDoService.addToDo(testDTO);
                });
        assertEquals("all fields must be filled", exception.getMessage());
    }
}