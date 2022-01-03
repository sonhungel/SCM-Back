package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class InvoiceDetailAlreadyExistException extends Exception {
    public InvoiceDetailAlreadyExistException(String message){
        super(message);
    }
}
