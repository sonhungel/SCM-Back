package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class SupplierNotFoundException extends Exception {
    private final Integer supplierNumber;
    public SupplierNotFoundException(String message, Integer supplierNumber){
        super(message);
        this.supplierNumber = supplierNumber;
    }
}
