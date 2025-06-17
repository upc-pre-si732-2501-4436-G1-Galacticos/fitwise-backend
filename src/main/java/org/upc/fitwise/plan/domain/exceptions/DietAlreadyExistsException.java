package org.upc.fitwise.plan.domain.exceptions;

public class DietAlreadyExistsException extends RuntimeException {
    public DietAlreadyExistsException(String title) {
        super("Diet with title '" + title + "' already exists.");
    }
}