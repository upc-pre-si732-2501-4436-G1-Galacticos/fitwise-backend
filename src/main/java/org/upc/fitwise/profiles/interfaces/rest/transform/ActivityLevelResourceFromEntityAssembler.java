package org.upc.fitwise.profiles.interfaces.rest.transform;


import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;
import org.upc.fitwise.profiles.interfaces.rest.resources.ActivityLevelResource;

public class ActivityLevelResourceFromEntityAssembler {
    public static ActivityLevelResource toResourceFromEntity(ActivityLevel entity) {
        return new ActivityLevelResource(entity.getId(), entity.getLevelName(), entity.getScore(),entity.getTagNamesActivityLevel());
    }
}
