package com.getir.assessment.readingisgood.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String object, String message) {
        super(String.format("Failed for [%s]: %s", object, message));
    }
}
