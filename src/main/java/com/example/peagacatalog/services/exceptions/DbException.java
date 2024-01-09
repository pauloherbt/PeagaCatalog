package com.example.peagacatalog.services.exceptions;

public class DbException extends RuntimeException{
    public DbException(String message) {
        super(message);
    }
}
