package org.upc.fitwise.profiles.domain.model.commands;

import java.math.BigDecimal;

public record UpdateProfileCommand(Long userId, String firstName, String lastName, String gender, BigDecimal height, BigDecimal weight, Long activityLevelId, Long  goalId) {
}
