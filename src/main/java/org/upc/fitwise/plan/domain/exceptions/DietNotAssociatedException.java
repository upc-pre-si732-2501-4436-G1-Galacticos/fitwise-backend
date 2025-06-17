package org.upc.fitwise.plan.domain.exceptions;

public class DietNotAssociatedException extends RuntimeException {
    public DietNotAssociatedException() {
        super("The diet is not associated with the plan.");
    }
}