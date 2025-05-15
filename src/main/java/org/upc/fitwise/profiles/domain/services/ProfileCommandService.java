package org.upc.fitwise.profiles.domain.services;

import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.profiles.domain.model.commands.CreateProfileCommand;
import org.upc.fitwise.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(UpdateProfileCommand command);
    Long handle(CreateProfileCommand command);

}
