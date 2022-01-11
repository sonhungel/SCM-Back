package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class UpdateException extends Exception {
    private final String messageFail;
    public UpdateException(String message){
        super(message);
        this.messageFail = message;
    }
}
