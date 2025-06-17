package org.upc.fitwise.plan.domain.exceptions;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(Long mealId) {
        super("Meal with ID " + mealId + " not found.");
    }
}