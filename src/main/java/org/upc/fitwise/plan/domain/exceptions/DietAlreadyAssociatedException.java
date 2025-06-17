package org.upc.fitwise.plan.domain.exceptions;

public class DietAlreadyAssociatedException extends RuntimeException {
    public DietAlreadyAssociatedException() {
        super("The diet is already associated with the plan.");
    }
}