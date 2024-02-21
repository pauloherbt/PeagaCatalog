package com.example.peagacatalog.resources.exceptions;

import com.example.peagacatalog.services.exceptions.DbException;
import com.example.peagacatalog.services.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest servletRequest){
        StandardError standardError = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value()
                , "Resource not found",e.getMessage(),servletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
    @ExceptionHandler(DbException.class)
    public ResponseEntity<StandardError> dbException(EntityNotFoundException e, HttpServletRequest servletRequest){
        StandardError standardError = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value()
                , "db exception",e.getMessage(),servletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e,HttpServletRequest servletRequest){
        ValidationError error = new ValidationError(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value()
                , "validation exception",e.getMessage(),servletRequest.getRequestURI());
        for (FieldError fieldError : e.getFieldErrors()) {
            error.addError(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> invalidRequestSyntax(MethodArgumentTypeMismatchException e, HttpServletRequest servletRequest){
        StandardError standardError = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value()
                , "Falha na requisição",e.getMessage(),servletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
}
