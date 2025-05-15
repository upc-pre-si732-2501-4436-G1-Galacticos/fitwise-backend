package org.upc.fitwise.profiles.interfaces.rest.transform;


import org.upc.fitwise.profiles.domain.model.aggregates.Goal;
import org.upc.fitwise.profiles.interfaces.rest.resources.GoalResource;

public class GoalResourceFromEntityAssembler {
    public static GoalResource toResourceFromEntity(Goal entity) {
        return new GoalResource(entity.getId(), entity.getGoalName(), entity.getScore());
    }
}
