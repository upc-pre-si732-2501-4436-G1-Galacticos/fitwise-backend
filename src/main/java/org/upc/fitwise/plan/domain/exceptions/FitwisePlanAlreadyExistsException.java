package org.upc.fitwise.plan.domain.exceptions;

public class FitwisePlanAlreadyExistsException extends RuntimeException {
    public FitwisePlanAlreadyExistsException(String title) {
        super("FitwisePlan with title '" + title + "' already exists.");
    }
}
