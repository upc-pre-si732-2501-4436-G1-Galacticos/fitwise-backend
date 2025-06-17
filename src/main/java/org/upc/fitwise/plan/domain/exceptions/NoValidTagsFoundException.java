package org.upc.fitwise.plan.domain.exceptions;

public class NoValidTagsFoundException extends RuntimeException {
    public NoValidTagsFoundException(String message) {
        super(message);
    }
}