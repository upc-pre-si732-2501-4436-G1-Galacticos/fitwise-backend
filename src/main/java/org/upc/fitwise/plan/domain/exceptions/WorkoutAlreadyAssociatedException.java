package org.upc.fitwise.plan.domain.exceptions;

public class WorkoutAlreadyAssociatedException extends RuntimeException {
    public WorkoutAlreadyAssociatedException() {
        super("The workout is already associated with the plan.");
    }
}
