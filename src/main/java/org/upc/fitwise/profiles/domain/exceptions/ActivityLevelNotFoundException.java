package org.upc.fitwise.profiles.domain.exceptions;

public class ActivityLevelNotFoundException extends RuntimeException {
    public ActivityLevelNotFoundException(Long aLong) {
        super("ActivityLevel with id " + aLong + " not found");
    }
}