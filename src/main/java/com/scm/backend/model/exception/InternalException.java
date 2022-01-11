package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class InternalException extends Exception {
    private final String messageFail;
    public InternalException(String message){
        super(message);
        this.messageFail = message;
    }
}
