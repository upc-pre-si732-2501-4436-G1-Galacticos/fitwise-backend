package org.upc.fitwise.profiles.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

public record ActivityLevelResource(Long id, String levelName, BigDecimal score, List<String> tags) {
}