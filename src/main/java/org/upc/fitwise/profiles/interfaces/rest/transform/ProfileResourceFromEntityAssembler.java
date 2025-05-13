package org.upc.fitwise.profiles.interfaces.rest.transform;


import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.profiles.interfaces.rest.resources.ProfileResource;



public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(entity.getId(), entity.getFullName(), entity.getGender(),entity.getHeight(),entity.getWeight(),entity.getActivityLevel().getId(),entity.getGoal().getId(),entity.getScore());
    }
}


