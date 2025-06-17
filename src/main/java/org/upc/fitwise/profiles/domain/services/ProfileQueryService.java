package org.upc.fitwise.profiles.domain.services;


import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByUserIdQuery;


import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByUserIdQuery query);
    Optional<Profile> handle(GetProfileByIdQuery query);
}
