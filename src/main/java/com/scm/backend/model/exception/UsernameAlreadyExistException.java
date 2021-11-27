package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistException extends Exception {
    private final String username;
    public UsernameAlreadyExistException(String message, String username){
        super(message);
        this.username = username;
    }
}
