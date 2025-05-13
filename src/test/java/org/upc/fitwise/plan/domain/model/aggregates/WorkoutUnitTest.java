package org.upc.fitwise.plan.domain.model.aggregates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class WorkoutUnitTest {
    @Test
    @DisplayName("Should create a Workout with correct name and description")
    void shouldCreateWorkout() {
        String name = "Full Body Workout";
        String description = "Strength training for all major muscle groups.";
        Workout workout = new Workout(name, description);
        assertEquals(name, workout.getTitle());
        assertEquals(description, workout.getDescription());
        assertNotNull(workout.getExercises());
        assertTrue(workout.getExercises().isEmpty());
    }

    @Test
    @DisplayName("Should add an exercise to the workout")
    void shouldAddExerciseToWorkout() {
        Workout workout = new Workout("Leg Day", "Focus on lower body.");
        Exercise squat = new Exercise("Squat", "Compound exercise for legs.");
        workout.addExercise(squat);
        List<Exercise> exercises = workout.getExercises();
        assertEquals(1, exercises.size());
        assertTrue(exercises.contains(squat));
    }

    @Test
    @DisplayName("Should not add the same exercise twice")
    void shouldNotAddSameExerciseTwice() {
        Workout workout = new Workout("Cardio", "Improves cardiovascular health.");
        Exercise running = new Exercise("Running", "High-intensity exercise.");
        workout.addExercise(running);
        workout.addExercise(running);
        assertEquals(1, workout.getExercises().size());
    }

    @Test
    @DisplayName("Should remove an exercise from the workout")
    void shouldRemoveExerciseFromWorkout() {
        Workout workout = new Workout("Upper Body", "Focus on chest, back, and arms.");
        Exercise benchPress = new Exercise("Bench Press", "Compound exercise for chest.");
        Exercise pullUp = new Exercise("Pull-Up", "Compound exercise for back.");
        workout.addExercise(benchPress);
        workout.addExercise(pullUp);

        workout.removeExercise(benchPress);
        List<Exercise> exercises = workout.getExercises();
        assertEquals(1, exercises.size());
        assertFalse(exercises.contains(benchPress));
        assertTrue(exercises.contains(pullUp));

        workout.removeExercise(pullUp);
        assertEquals(0, exercises.size());
        assertFalse(exercises.contains(pullUp));
    }

    @Test
    @DisplayName("Should not change the workout if trying to remove a non-existent exercise")
    void shouldNotChangeWorkoutIfRemovingNonExistentExercise() {
        Workout workout = new Workout("Core Workout", "Focus on abdominal muscles.");
        Exercise plank = new Exercise("Plank", "Isometric exercise for core.");
        Exercise sitUp = new Exercise("Sit-Up", "Dynamic exercise for core.");
        workout.addExercise(plank);
        int initialSize = workout.getExercises().size();
        workout.removeExercise(sitUp); // Trying to remove an exercise that wasn't added
        assertEquals(initialSize, workout.getExercises().size());
        assertTrue(workout.getExercises().contains(plank));
        assertFalse(workout.getExercises().contains(sitUp));
    }
}