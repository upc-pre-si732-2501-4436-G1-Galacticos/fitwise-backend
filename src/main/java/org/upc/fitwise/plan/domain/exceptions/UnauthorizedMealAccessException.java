package org.upc.fitwise.plan.domain.exceptions;

public class UnauthorizedMealAccessException extends RuntimeException {
    public UnauthorizedMealAccessException(String message) {
        super(message);
    }
}