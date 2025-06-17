package org.upc.fitwise.plan.domain.exceptions;

public class MealAlreadyExistsException extends RuntimeException {
    public MealAlreadyExistsException(String title) {
        super("Meal with title '" + title + "' already exists.");
    }
}