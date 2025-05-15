package org.upc.fitwise.profiles.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.profiles.domain.exceptions.ActivityLevelNotFoundException;
import org.upc.fitwise.profiles.domain.exceptions.GoalNotFoundException;
import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;
import org.upc.fitwise.profiles.domain.model.aggregates.Goal;
import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.profiles.domain.model.commands.CreateProfileCommand;
import org.upc.fitwise.profiles.domain.model.commands.UpdateProfileCommand;
import org.upc.fitwise.profiles.domain.model.queries.GetProfileByUserIdQuery;
import org.upc.fitwise.profiles.domain.model.valueobjects.PersonName;
import org.upc.fitwise.profiles.domain.services.ProfileCommandService;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.ActivityLevelRepository;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.GoalRepository;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;
    private final ActivityLevelRepository activityLevelRepository;
    private final GoalRepository goalRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository,ActivityLevelRepository activityLevelRepository,GoalRepository goalRepository) {
        this.profileRepository = profileRepository;
        this.activityLevelRepository = activityLevelRepository;
        this.goalRepository = goalRepository;
    }


    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        var fullName = new PersonName(command.firstName(), command.lastName());
        var result = profileRepository.findByUserId(command.userId());
        if (result.isEmpty()) throw new IllegalArgumentException("Profile does not exist");
        var profileToUpdate = result.get();
        try {

            ActivityLevel activityLevel = activityLevelRepository.findById(command.activityLevelId()).orElseThrow(() -> new ActivityLevelNotFoundException(command.activityLevelId()));
            Goal goal = goalRepository.findById(command.goalId()).orElseThrow(() -> new GoalNotFoundException(command.goalId()));

            var updatedProfile = profileRepository.save(profileToUpdate.updateInformation(
                    command.firstName(),
                    command.lastName(),
                    command.gender(),
                    command.height(),
                    command.weight(),activityLevel,goal));

            return Optional.of(updatedProfile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating profile: " + e.getMessage());
        }
    }

    @Override
    public Long handle(CreateProfileCommand command) {

        if (profileRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("Profile already exists");
        }
        ActivityLevel activityLevel = activityLevelRepository.findById(command.activityLevelId()).orElseThrow(() -> new ActivityLevelNotFoundException(command.activityLevelId()));
        Goal goal = goalRepository.findById(command.activityLevelId()).orElseThrow(() -> new GoalNotFoundException(command.activityLevelId()));
        var profile = new Profile(command.firstName(),command.lastName(),command.gender(),command.height(),command.weight(),activityLevel,goal,command.userId());
        try {
            profileRepository.save(profile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving profile: " + e.getMessage());
        }
        return profile.getId();
    }





}