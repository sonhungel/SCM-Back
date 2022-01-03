package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class EmailNotExistException extends Exception {
    private final String email;
    public EmailNotExistException(String message, String email){
        super(message);
        this.email = email;
    }
}
