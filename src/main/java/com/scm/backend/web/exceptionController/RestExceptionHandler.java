package com.scm.backend.web.exceptionController;

import com.scm.backend.model.exception.ItemNumberAlreadyExistException;
import com.scm.backend.model.exception.ItemNumberLessThanOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class RestError {
        private HttpStatus status;
        private List<ErrorDetail> errors;

        public RestError(HttpStatus status) {
            this.status = status;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        static class ErrorDetail {
            String error;
            String message;
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<RestError.ErrorDetail> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new RestError.ErrorDetail(error.getField(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new RestError.ErrorDetail(error.getObjectName(), error.getDefaultMessage()));
        }

        RestError apiError =
                new RestError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestError handleException(Exception ex) {
        ex.printStackTrace();

        return new RestError(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(new RestError.ErrorDetail(ex.getClass().getName(), ex.getMessage())));
    }

    @ExceptionHandler(ItemNumberAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestError handleItemNumberAlreadyExistException(ItemNumberAlreadyExistException ex) {
        return new RestError(HttpStatus.CONFLICT, Arrays.asList(new RestError.ErrorDetail("itemNumberAlreadyExist", "Item number already exist.")));
    }

    @ExceptionHandler(ItemNumberLessThanOne.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestError handleItemNumberLessThanOne(ItemNumberLessThanOne ex) {
        return new RestError(HttpStatus.CONFLICT, Arrays.asList(new RestError.ErrorDetail("itemNumberLessThanOne", "Item number less than 1.")));
    }

}
