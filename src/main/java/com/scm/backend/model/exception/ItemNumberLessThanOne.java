package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ItemNumberLessThanOne extends Exception{
    private final Integer itemNumber;
    public ItemNumberLessThanOne(String message, Integer itemNumber){
        super(message);
        this.itemNumber = itemNumber;
    }
}