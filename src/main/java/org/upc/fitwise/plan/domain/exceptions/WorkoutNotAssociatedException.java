package org.upc.fitwise.plan.domain.exceptions;

public class WorkoutNotAssociatedException extends RuntimeException {
    public WorkoutNotAssociatedException() {
        super("The workout is not associated with the plan.");
    }
}