package org.upc.fitwise.plan.domain.exceptions;

public class ExerciseAlreadyExistsException extends RuntimeException {
    public ExerciseAlreadyExistsException(String title) {
        super("Exercise with title '" + title + "' already exists.");
    }
}