package com.example.backend;

import com.example.backend.security.exceptions.PasswordValidationException;
import com.example.backend.security.exceptions.UsernameIsTakenException;
import com.example.backend.todos.exceptions.ToDoNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            UsernameIsTakenException.class,
            PasswordValidationException.class,
    })
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler({
            ToDoNotFoundException.class,
    })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


}
