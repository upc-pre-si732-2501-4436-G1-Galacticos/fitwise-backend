package org.upc.fitwise.plan.domain.exceptions;

public class UnauthorizedDietAccessException extends RuntimeException {
    public UnauthorizedDietAccessException(String message) {
        super(message);
    }
}