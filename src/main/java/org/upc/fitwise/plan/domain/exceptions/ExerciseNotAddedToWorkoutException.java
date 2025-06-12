package org.upc.fitwise.plan.domain.exceptions;

public class ExerciseNotAddedToWorkoutException extends RuntimeException {
    public ExerciseNotAddedToWorkoutException() {
        super("This exercise is not added to the workout.");
    }
}