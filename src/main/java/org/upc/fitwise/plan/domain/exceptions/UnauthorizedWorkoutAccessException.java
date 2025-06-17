package org.upc.fitwise.plan.domain.exceptions;

public class UnauthorizedWorkoutAccessException extends RuntimeException {
    public UnauthorizedWorkoutAccessException(String message) {
        super(message);
    }
}