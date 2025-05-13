package org.upc.fitwise.plan.domain.model.aggregates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MealUnitTest {

    @Test
    @DisplayName("Should create a Meal with correct name and description")
    void shouldCreateMeal() {
        String name = "Chicken Breast with Broccoli";
        String description = "Lean protein and vegetables";
        Meal meal = new Meal(name, description);
        assertEquals(name, meal.getTitle());
        assertEquals(description, meal.getDescription());
        assertNotNull(meal.getDiets());
        assertTrue(meal.getDiets().isEmpty());
    }
}