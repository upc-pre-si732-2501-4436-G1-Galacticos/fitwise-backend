package org.upc.fitwise.profiles.interfaces.rest.resources;

import java.math.BigDecimal;

public record UpdateProfileResource(String firstName, String lastName, String gender, BigDecimal height, BigDecimal weight, Long activityLevelId, Long  goalId) {
}
