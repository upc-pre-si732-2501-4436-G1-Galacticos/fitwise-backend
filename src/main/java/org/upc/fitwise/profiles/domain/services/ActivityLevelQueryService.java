package org.upc.fitwise.profiles.domain.services;


import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;
import org.upc.fitwise.profiles.domain.model.queries.GetAllActivityLevelsQuery;

import java.util.List;
import java.util.Optional;

public interface ActivityLevelQueryService {
    List<ActivityLevel> handle(GetAllActivityLevelsQuery query);
}
