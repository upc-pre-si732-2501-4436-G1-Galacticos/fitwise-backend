package org.upc.fitwise.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByIdQuery;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByUserIdQuery;
import org.upc.fitwise.profiles.domain.services.ProfileQueryService;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    /**
     * Query handler to get Profile by userId
     *
     * @param query containing userId
     * @return Profile
     */
    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {
        return profileRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

}
