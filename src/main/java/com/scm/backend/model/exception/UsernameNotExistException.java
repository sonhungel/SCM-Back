package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class UsernameNotExistException extends Exception {
    private final String username;
    public UsernameNotExistException(String message, String username){
        super(message);
        this.username = username;
    }
}
