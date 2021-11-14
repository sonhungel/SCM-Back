package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ItemNumberNotFoundException extends Exception {
    private final Integer itemNumber;
    public ItemNumberNotFoundException(String message, Integer itemNumber){
        super(message);
        this.itemNumber = itemNumber;
    }
}
