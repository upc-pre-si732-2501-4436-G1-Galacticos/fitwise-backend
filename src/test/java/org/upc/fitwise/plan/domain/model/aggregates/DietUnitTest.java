package org.upc.fitwise.plan.domain.model.aggregates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietUnitTest {

    @Test
    @DisplayName("Should create a Diet with correct name and description")
    void shouldCreateDiet() {
        String name = "Keto Diet";
        String description = "High-fat, low-carb diet";
        Diet diet = new Diet(name, description);
        assertEquals(name, diet.getTitle());
        assertEquals(description, diet.getDescription());
        assertNotNull(diet.getMeals());
        assertTrue(diet.getMeals().isEmpty());
    }

}