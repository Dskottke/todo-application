package com.example.backend;

import com.example.backend.todos.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
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
        Authentication authentication = new UsernamePasswordAuthenticationToken("testName", "testPassword");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<ToDo> expected = Collections.emptyList();
        //WHEN
        when(toDoRepository.findAllByUsername("testName")).thenReturn(expected);
        List<ToDo> actual = toDoService.getAllToDos();
        //THEN
        verify(toDoRepository).findAllByUsername("testName");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AddToDo with a newToDo DTO and expect the new ToDo as a return")
    @WithMockUser(username = "testName")
    void addToDoWithDtoAndExpectToDo() {
        //GIVEN
        Authentication authentication = new UsernamePasswordAuthenticationToken("testName", "testPassword");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        NewToDo testDTO = new NewToDo("testDescription", "testTitle", LocalDate.now().plusDays(1));
        //WHEN
        ToDo expected = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.OPEN,
                LocalDate.now(),
                LocalDate.now().plusDays(1));
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
        NewToDo testDTO = new NewToDo("", "", LocalDate.now());
        //WHEN

        //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> toDoService.addToDo(testDTO));
        assertEquals("Title and description cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("UpdateStatusById with valid id and expect the updated ToDo with status IN_PROGRESS as a return")
    void updateStatusByIdShouldReturnUpdatedToDoWithStatusInProgress() {
        //GIVEN
        String id = "testId";
        //WHEN
        ToDo oldToDo = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.OPEN,
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        ToDo expected = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(1));

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
        ToDo oldToDo = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        ToDo expected = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.DONE,
                LocalDate.now(),
                LocalDate.now().plusDays(1));

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
        ToDo oldToDo = new ToDo(
                "testId",
                "testName",
                "testDescription",
                "testTitle",
                Status.DONE,
                LocalDate.now(),
                LocalDate.now().plusDays(1));

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
        Authentication authentication = new UsernamePasswordAuthenticationToken("testName", "testPassword");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(toDoRepository.findAllByUsername("testName")).thenReturn(List.of(
                new ToDo(
                        "testId",
                        "testName",
                        "testDescription",
                        "testTitle",
                        Status.OPEN,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1))));

        int sizeBefore = toDoService.getAllToDos().size();
        //WHEN
        when(toDoRepository.existsById(id)).thenReturn(true);
        doNothing().when(toDoRepository).deleteById(id);
        when(toDoRepository.findAllByUsername("testName")).thenReturn(Collections.emptyList());
        toDoService.deleteToDoById(id);
        int sizeAfter = toDoService.getAllToDos().size();
        //THEN
        verify(toDoRepository).existsById(id);
        verify(toDoRepository, times(2)).findAllByUsername("testName");
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
