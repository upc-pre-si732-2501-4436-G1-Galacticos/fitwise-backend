package org.upc.fitwise.plan.domain.exceptions;

public class UnauthorizedExerciseAccessException extends RuntimeException {
    public UnauthorizedExerciseAccessException(String message) {
        super(message);
    }
}