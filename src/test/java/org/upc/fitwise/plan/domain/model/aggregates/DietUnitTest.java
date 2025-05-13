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

    @Test
    @DisplayName("Should add a meal to the diet")
    void shouldAddMealToDiet() {
        Diet diet = new Diet("Mediterranean Diet", "Healthy eating plan");
        Meal breakfast = new Meal("Greek Yogurt with Berries", "High in protein and antioxidants");
        diet.addMeal(breakfast);
        List<Meal> meals = diet.getMeals();
        assertEquals(1, meals.size());
        assertTrue(meals.contains(breakfast));
    }

    @Test
    @DisplayName("Should not add the same meal twice")
    void shouldNotAddSameMealTwice() {
        Diet diet = new Diet("Vegan Diet", "Plant-based eating");
        Meal lunch = new Meal("Lentil Soup", "Rich in fiber and protein");
        diet.addMeal(lunch);
        diet.addMeal(lunch);
        assertEquals(1, diet.getMeals().size());
    }


    @Test
    @DisplayName("Should remove a meal from the diet")
    void shouldRemoveMealFromDiet() {
        Diet diet = new Diet("Balanced Diet", "Includes various food groups");
        Meal breakfast = new Meal("Oatmeal with Fruits", "Good source of fiber");
        Meal lunch = new Meal("Chicken Salad Sandwich", "Provides protein");
        diet.addMeal(breakfast);
        diet.addMeal(lunch);

        diet.removeMeal(breakfast);
        List<Meal> meals = diet.getMeals();
        assertEquals(1, meals.size());
        assertFalse(meals.contains(breakfast));
        assertTrue(meals.contains(lunch));

        diet.removeMeal(lunch);
        assertEquals(0, meals.size());
        assertFalse(meals.contains(lunch));
    }

    @Test
    @DisplayName("Should not change the diet if trying to remove a non-existent meal")
    void shouldNotChangeDietIfRemovingNonExistentMeal() {
        Diet diet = new Diet("Special Diet", "For specific needs");
        Meal meal1 = new Meal("Special Item 1", "...");
        Meal meal2 = new Meal("Special Item 2", "...");
        diet.addMeal(meal1);
        int initialSize = diet.getMeals().size();
        diet.removeMeal(meal2); // Trying to remove a meal that wasn't added
        assertEquals(initialSize, diet.getMeals().size());
        assertTrue(diet.getMeals().contains(meal1));
        assertFalse(diet.getMeals().contains(meal2));
    }
}