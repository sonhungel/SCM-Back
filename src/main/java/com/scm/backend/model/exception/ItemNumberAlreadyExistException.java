package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ItemNumberAlreadyExistException extends Exception {
    private final Integer itemNumber;
    public ItemNumberAlreadyExistException(String message, Integer itemNumber){
        super(message);
        this.itemNumber = itemNumber;
    }
}
