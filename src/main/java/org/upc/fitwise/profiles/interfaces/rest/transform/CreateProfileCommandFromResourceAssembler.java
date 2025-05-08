package org.upc.fitwise.profiles.interfaces.rest.transform;


import org.upc.fitwise.profiles.domain.model.commands.CreateProfileCommand;
import org.upc.fitwise.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(Long userId,CreateProfileResource resource) {
        return new CreateProfileCommand(userId,resource.firstName(), resource.lastName(),resource.gender(),resource.height(),resource.weight(),resource.activityLevelId(),resource.goalId());
    }
}
