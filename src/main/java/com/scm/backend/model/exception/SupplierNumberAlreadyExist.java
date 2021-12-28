package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class SupplierNumberAlreadyExist extends Exception {
    private final Integer supplierNumber;
    public SupplierNumberAlreadyExist(String message, Integer supplierNumber){
        super(message);
        this.supplierNumber = supplierNumber;
    }
}
