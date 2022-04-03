package com.getir.assessment.readingisgood.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationException extends BaseException{
    public ValidationException(String message) {
        super(message);
    }
}
