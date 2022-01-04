package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class CustomerNumberNotFoundException extends Exception {
    private final Integer customerNumber;
    public CustomerNumberNotFoundException(String message, Integer customerNumber){
        super(message);
        this.customerNumber = customerNumber;
    }
}
