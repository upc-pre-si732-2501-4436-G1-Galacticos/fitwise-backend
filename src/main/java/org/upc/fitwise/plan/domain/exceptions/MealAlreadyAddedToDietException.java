package org.upc.fitwise.plan.domain.exceptions;

public class MealAlreadyAddedToDietException extends RuntimeException {
    public MealAlreadyAddedToDietException() {
        super("This meal is already added to the diet.");
    }
}