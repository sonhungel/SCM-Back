package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ConcurrentUpdateException extends Exception {
    public ConcurrentUpdateException(String message){
        super(message);
    }
}
