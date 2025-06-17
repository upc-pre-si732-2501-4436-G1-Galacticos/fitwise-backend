package org.upc.fitwise.plan.domain.exceptions;

public class UnauthorizedPlanAccessException extends RuntimeException {
    public UnauthorizedPlanAccessException(String message) {
        super(message);
    }
}