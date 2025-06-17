package org.upc.fitwise.plan.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.exceptions.*;
import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.commands.*;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.MealRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;

import java.util.Optional;


@Service
public class DietCommandServiceImpl implements DietCommandService {

    private final DietRepository dietRepository;
    private final MealRepository mealRepository;
    private final ExternalIamService externalIamService;


    public DietCommandServiceImpl(DietRepository dietRepository,MealRepository mealRepository,ExternalIamService externalIamService){
        this.dietRepository=dietRepository;
        this.mealRepository=mealRepository;
        this.externalIamService= externalIamService;
    }


    @Override
    public void handle(AddMealToDietCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedDietAccessException("User not found or unauthorized for this operation."));

        Diet diet = dietRepository.findById(command.dietId())
                .orElseThrow(() -> new DietNotFoundException(command.dietId()));

        if (!userId.equals(diet.getUserId())) {
            throw new UnauthorizedDietAccessException("Add not permitted: Diet belongs to a different user");
        }
        Meal mealToAdd  = mealRepository.findById(command.mealId())
                .orElseThrow(() -> new MealNotFoundException(command.mealId()));

        if (diet.getMeals().contains(mealToAdd)) {
            throw new MealAlreadyAddedToDietException();
        }

        diet.addMealToDiet(mealToAdd);
        dietRepository.save(diet);
        System.out.println("Meal added to diet");
    }


    @Override
    public void handle(RemoveMealToDietCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedDietAccessException("User not found or unauthorized for this operation."));

        Diet diet = dietRepository.findById(command.dietId())
                .orElseThrow(() -> new DietNotFoundException(command.dietId()));

        if (!userId.equals(diet.getUserId())) {
            throw new UnauthorizedDietAccessException("Remove not permitted: Diet belongs to a different user");
        }
        Meal mealToRemove  = mealRepository.findById(command.mealId())
                .orElseThrow(() -> new MealNotFoundException(command.mealId()));

        if (!diet.getMeals().contains(mealToRemove)) {
            throw new MealNotAddedToDietException();
        }

        diet.removeMealToDiet(mealToRemove);
        dietRepository.save(diet);
        System.out.println("Meal Removed from diet");
    }


    @Override
    public Long handle(CreateDietCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedDietAccessException("User not found or unauthorized for this operation."));

        if (dietRepository.existsByTitle(command.title())) {
            throw new DietAlreadyExistsException(command.title());
        }

        var diet = new Diet(command.title(), command.description(), userId);

        dietRepository.save(diet);
        return diet.getId();
    }

    @Override
    public Optional<Diet> handle(UpdateDietCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedDietAccessException("User not found or unauthorized for this operation."));

        if (dietRepository.existsByTitleAndIdIsNot(command.title(), command.dietId())) {
            throw new DietAlreadyExistsException(command.title());
        }

        var result = dietRepository.findById(command.dietId());
        if (result.isEmpty()) {
            throw new DietNotFoundException(command.dietId());
        }

        var dietToUpdate = result.get();
        if (!userId.equals(dietToUpdate.getUserId())) {
            throw new UnauthorizedDietAccessException("Update not permitted: Diet belongs to a different user");
        }

        var updatedDiet = dietRepository.save(dietToUpdate.updateInformation(command.title(), command.description()));
        return Optional.of(updatedDiet);
    }
    }


