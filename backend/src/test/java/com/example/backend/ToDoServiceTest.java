package com.example.backend;

import com.example.backend.exception.ToDoNotFoundException;
import com.example.backend.model.NewToDo;
import com.example.backend.model.Status;
import com.example.backend.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> toDoService.addToDo(testDTO));
        assertEquals("all fields must be filled", exception.getMessage());
    }

    @Test
    @DisplayName("UpdateStatusById with valid id and expect the updated ToDo with status IN_PROGRESS as a return")
    void updateStatusByIdShouldReturnUpdatedToDoWithStatusInProgress() {
        //GIVEN
        String id = "testId";
        //WHEN
        ToDo oldToDo = new ToDo("testId", "testDescription", "testTitle", Status.OPEN);
        ToDo expected = new ToDo("testId", "testDescription", "testTitle", Status.IN_PROGRESS);
        when(toDoRepository.findById("testId")).thenReturn(Optional.of(oldToDo));
        when(toDoRepository.save(expected)).thenReturn(expected);
        ToDo actual = toDoService.updateStatusById(id);
        //THEN
        verify(toDoRepository).findById(id);
        verify(toDoRepository).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("UpdateStatusById with valid id and expect the updated ToDo with status DONE as a return")
    void updateStatusByIdShouldReturnUpdatedToDoWithStatusDone() {
        //GIVEN
        String id = "testId";
        //WHEN
        ToDo oldToDo = new ToDo("testId", "testDescription", "testTitle", Status.IN_PROGRESS);
        ToDo expected = new ToDo("testId", "testDescription", "testTitle", Status.DONE);
        when(toDoRepository.findById("testId")).thenReturn(Optional.of(oldToDo));
        when(toDoRepository.save(expected)).thenReturn(expected);
        ToDo actual = toDoService.updateStatusById(id);
        //THEN
        verify(toDoRepository).findById(id);
        verify(toDoRepository).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("UpdateStatusById with valid id but invalid status should throw IllegalStateException")
    void updateStatusByIdWithInvalidStatusDoneShouldThrowException() {
        //GIVEN
        String id = "testId";
        //WHEN
        ToDo oldToDo = new ToDo("testId", "testDescription", "testTitle", Status.DONE);
        when(toDoRepository.findById("testId")).thenReturn(Optional.of(oldToDo));
        //THEN
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> toDoService.updateStatusById(id));
        assertEquals("invalid status", exception.getMessage());

    }

    @Test
    @DisplayName("UpdateStatusById with invalid id should throw a ToDoNotFoundException")
    void updateStatusByIdShouldThrowToDoNotFoundException() {
        //GIVEN
        String id = "testId";
        //WHEN

        //THEN
        ToDoNotFoundException exception = assertThrows(ToDoNotFoundException.class, () -> toDoService.updateStatusById(id));
        assertEquals("Could not find todo testId", exception.getMessage());
    }

    @Test
    @DisplayName("DeleteById : List of ToDos should be smaller after deleting a ToDo")
    void deleteByIdAndListShouldBeSmallerAfterDeletingAToDo() {
        //GIVEN
        String id = "testId";
        when(toDoRepository.findAll()).thenReturn(List.of(new ToDo("testId", "testDescription", "testTitle", Status.OPEN)));
        int sizeBefore = toDoService.getAllToDos().size();
        //WHEN
        when(toDoRepository.existsById(id)).thenReturn(true);
        doNothing().when(toDoRepository).deleteById(id);
        when(toDoRepository.findAll()).thenReturn(Collections.emptyList());
        toDoService.deleteToDoById(id);
        int sizeAfter = toDoService.getAllToDos().size();
        //THEN
        verify(toDoRepository).existsById(id);
        verify(toDoRepository, times(2)).findAll();
        verify(toDoRepository).deleteById(id);
        assertTrue(sizeBefore > sizeAfter);
    }

    @Test
    @DisplayName("DeleteById : should throw a ToDoNotFoundException")
    void deleteByIdShouldThrowAToDoNotFoundException() {
        //GIVEN
        String id = "testId";
        //WHEN
        //THEN
        ToDoNotFoundException exception = assertThrows(ToDoNotFoundException.class, () -> toDoService.deleteToDoById(id));
        assertEquals("Could not find todo testId", exception.getMessage());
    }
}
