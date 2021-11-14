package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ConcurrentUpdateItemException extends Exception {
    private final Long itemId;
    public ConcurrentUpdateItemException(String message, Long itemId){
        super(message);
        this.itemId = itemId;
    }
}
