package com.scm.backend.model.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto {
    private final String message;
    private final HttpStatus status;
    private final Object data;

    public ResponseDto(String message, HttpStatus status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
