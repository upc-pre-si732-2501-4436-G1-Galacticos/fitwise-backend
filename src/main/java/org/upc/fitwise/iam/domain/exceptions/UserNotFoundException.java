package org.upc.fitwise.iam.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) { // Puede ser email o ID
        super("User with identifier '" + identifier + "' not found.");
    }
}