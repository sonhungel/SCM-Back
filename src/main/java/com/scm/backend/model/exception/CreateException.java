package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class CreateException extends Exception {
    private final String messageFail;
    public CreateException(String message){
        super(message);
        this.messageFail = message;
    }
}
