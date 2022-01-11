package com.scm.backend.web.exceptionController;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.scm.backend.model.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(UsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("usernameAlreadyExistException", ex.getMessage())));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("usernameNotFoundException", ex.getMessage())));
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleSupplierNotFoundException(SupplierNotFoundException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("supplierNotFoundException", ex.getMessage())));
    }

    @ExceptionHandler(SupplierNumberAlreadyExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleSupplierNotFoundException(SupplierNumberAlreadyExist ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("supplierNumberAlreadyExist", ex.getMessage())));
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleMismatchedInputException(MismatchedInputException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("mismatchedInputException", ex.getMessage())));
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("invoiceNotFoundException", ex.getMessage())));
    }

    @ExceptionHandler(InvoiceDetailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleInvoiceDetailAlreadyExist(InvoiceDetailAlreadyExistException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("invoiceDetailAlreadyExistException", ex.getMessage())));
    }

    @ExceptionHandler(EmailNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleEmailNotExistException(EmailNotExistException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("emailNotExistException", ex.getMessage())));
    }

    @ExceptionHandler(CustomerNumberAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleCustomerNumberAlreadyExistException(CustomerNumberAlreadyExistException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("customerNumberAlreadyExistException", ex.getMessage())));
    }

    @ExceptionHandler(CustomerNumberNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleCustomerNumberNotFoundException(CustomerNumberNotFoundException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("customerNumberNotFoundException", ex.getMessage())));
    }

    @ExceptionHandler(ConcurrentUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleConcurrentUpdateException(ConcurrentUpdateException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("concurrentUpdateException", ex.getMessage())));
    }

    @ExceptionHandler(UpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleUpdateException(UpdateException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("updateException", ex.getMessage())));
    }

    @ExceptionHandler(InternalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestError handleInternalException(InternalException ex) {
        return new RestError(HttpStatus.BAD_REQUEST, Arrays.asList(new RestError.ErrorDetail("internalException", ex.getMessage())));
    }

}
