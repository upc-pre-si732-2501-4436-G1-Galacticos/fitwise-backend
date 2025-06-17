package org.upc.fitwise.plan.domain.exceptions;

public class MealNotAddedToDietException extends RuntimeException {
    public MealNotAddedToDietException() {
        super("This meal is not added to the diet.");
    }
}