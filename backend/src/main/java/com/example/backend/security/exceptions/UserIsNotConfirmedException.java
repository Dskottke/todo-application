package com.example.backend.security.exceptions;

import org.springframework.security.core.AuthenticationException;


public class UserIsNotConfirmedException extends AuthenticationException {
    public UserIsNotConfirmedException(String explanation) {
        super(explanation);
    }
}

