package org.upc.fitwise.plan.domain.exceptions;

public class ExerciseAlreadyAddedToWorkoutException extends RuntimeException {
    public ExerciseAlreadyAddedToWorkoutException() {
        super("This exercise is already added to the workout.");
    }
}