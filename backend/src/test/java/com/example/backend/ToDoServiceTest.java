package com.example.backend;

import com.example.backend.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ToDoServiceTest {

    private final ToDoRepository toDoRepository = Mockito.mock(ToDoRepository.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository);

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
        assertEquals(expected,actual);
    }
}