package org.upc.fitwise.profiles.application.internal.queryservices;
import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;
import org.upc.fitwise.profiles.domain.model.queries.GetAllActivityLevelsQuery;
import org.upc.fitwise.profiles.domain.services.ActivityLevelQueryService;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.ActivityLevelRepository;

import java.util.List;
@Service
public class ActivityLevelQueryServiceImpl implements ActivityLevelQueryService {

    private final ActivityLevelRepository activityLevelRepository;

    public ActivityLevelQueryServiceImpl(ActivityLevelRepository activityLevelRepository) {
        this.activityLevelRepository = activityLevelRepository;
    }


    @Override
    public List<ActivityLevel> handle(GetAllActivityLevelsQuery query) {
        return activityLevelRepository.findAll();
    }
}
