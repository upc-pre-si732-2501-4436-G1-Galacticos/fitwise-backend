package org.upc.fitwise.profiles.domain.exceptions;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException(Long aLong) {
        super("Goal with id " + aLong + " not found");
    }
}