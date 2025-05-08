package org.upc.fitwise.profiles.interfaces.rest.resources;

import java.math.BigDecimal;

public record ActivityLevelResource(Long id, String levelName, BigDecimal score) {
}