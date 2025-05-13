package org.upc.fitwise.profiles.interfaces.rest.resources;


import java.math.BigDecimal;

public record ProfileResource(Long id, String fullName, String gender, BigDecimal height, BigDecimal weight, Long activityLevelId,Long  goalId,BigDecimal score) {
}