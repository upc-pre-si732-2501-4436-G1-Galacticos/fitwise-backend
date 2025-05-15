package org.upc.fitwise.profiles.interfaces.rest.resources;

import java.math.BigDecimal;

public record GoalResource(Long id, String goalName, BigDecimal score) {
}