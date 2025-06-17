package org.upc.fitwise.plan.domain.exceptions;

public class FitwisePlanNotFoundException extends RuntimeException {
    public FitwisePlanNotFoundException(Long planId) {
        super("FitwisePlan with ID " + planId + " not found.");
    }
}
