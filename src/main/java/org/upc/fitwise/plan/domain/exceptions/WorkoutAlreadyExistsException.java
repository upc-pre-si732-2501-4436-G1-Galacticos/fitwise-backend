package org.upc.fitwise.plan.domain.exceptions;

public class WorkoutAlreadyExistsException extends RuntimeException {
    public WorkoutAlreadyExistsException(String title) {
        super("Workout with title '" + title + "' already exists.");
    }
}