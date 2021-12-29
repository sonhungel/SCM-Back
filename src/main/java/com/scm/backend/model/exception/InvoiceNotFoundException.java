package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class InvoiceNotFoundException extends Exception {
    private final Long id;
    public InvoiceNotFoundException(String message, Long id){
        super(message);
        this.id = id;
    }
}
