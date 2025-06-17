package org.upc.fitwise.plan.domain.model.aggregates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class ExerciseUnitTest {
    @Test
    @DisplayName("Should create an Exercise with correct name and description")
    void shouldCreateExercise() {
        String name = "Running";
        String description = "Cardiovascular exercise";
        Exercise exercise = new Exercise(name,description);
        assertEquals(name, exercise.getTitle());
        assertNotNull(exercise.getWorkouts());
        assertTrue(exercise.getWorkouts().isEmpty());
    }
}