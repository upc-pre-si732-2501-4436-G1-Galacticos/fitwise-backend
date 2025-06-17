package org.upc.fitwise.profiles.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

public record GoalResource(Long id, String goalName, BigDecimal score, List<String> tags) {
}