package org.upc.fitwise.plan.domain.exceptions;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(Long exerciseId) {
        super("Exercise with ID " + exerciseId + " not found.");
    }
}