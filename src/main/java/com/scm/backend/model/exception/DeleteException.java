package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class DeleteException extends Exception{
    private final String messageFail;
    public DeleteException(String message){
        super(message);
        this.messageFail = message;
    }
}
