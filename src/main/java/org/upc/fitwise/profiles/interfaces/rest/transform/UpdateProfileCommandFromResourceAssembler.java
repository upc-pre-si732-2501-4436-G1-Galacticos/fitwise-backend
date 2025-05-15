package org.upc.fitwise.profiles.interfaces.rest.transform;


import org.upc.fitwise.profiles.domain.model.commands.UpdateProfileCommand;
import org.upc.fitwise.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long userId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(userId, resource.firstName(), resource.lastName(),resource.gender(),resource.height(),resource.weight(),resource.activityLevelId(),resource.goalId());
    }
}