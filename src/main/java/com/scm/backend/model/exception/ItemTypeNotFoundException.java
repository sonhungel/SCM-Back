package com.scm.backend.model.exception;

import lombok.Getter;

@Getter
public class ItemTypeNotFoundException extends Exception{
    final String typeName;
    public ItemTypeNotFoundException(String message, String typeName){
        super(message);
        this.typeName = typeName;
    }
}
