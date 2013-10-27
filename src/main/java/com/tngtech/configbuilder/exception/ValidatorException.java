package com.tngtech.configbuilder.exception;

import javax.validation.ConstraintViolation;

import java.util.HashSet;
import java.util.Set;

public class ValidatorException extends RuntimeException {

    Set<ConstraintViolation> constraintViolations;

    public <T> ValidatorException(String message, Set<ConstraintViolation<T>> constraintViolations) {
        super(message);
        this.constraintViolations = new HashSet<ConstraintViolation>(constraintViolations); 
    }

    public ValidatorException(String message, Throwable e) {
        super(message, e);
    }
}
