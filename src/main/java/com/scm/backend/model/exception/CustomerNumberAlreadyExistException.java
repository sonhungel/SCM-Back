package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class CustomerNumberAlreadyExistException extends Exception {
    private final Integer customerNumber;
    public CustomerNumberAlreadyExistException(String message, Integer customerNumber){
        super(message);
        this.customerNumber = customerNumber;
    }
}
