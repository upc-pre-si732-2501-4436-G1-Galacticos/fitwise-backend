package org.upc.fitwise.plan.domain.exceptions;

public class WorkoutNotFoundException extends RuntimeException { // Corregido: 'class' y 'extends RuntimeException'
    public WorkoutNotFoundException(Long workoutId) { // Corregido: Parámetro en el constructor
        super("Workout with ID " + workoutId + " not found.");
    }
}