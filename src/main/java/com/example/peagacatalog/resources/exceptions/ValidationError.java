package com.example.peagacatalog.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
    private List<FieldMessage> errors;

    public ValidationError(Instant instant, Integer status, String error, String message, String path) {
        super(instant, status, error, message, path);
        errors = new ArrayList<>();
    }
    public List<FieldMessage> getErrors() {
        return errors;
    }
    public void addError(String field,String message){
     errors.add(new FieldMessage(field,message));
    }
}
