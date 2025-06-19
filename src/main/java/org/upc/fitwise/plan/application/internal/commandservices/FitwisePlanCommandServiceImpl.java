package org.upc.fitwise.plan.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.model.aggregates.*;
import org.upc.fitwise.plan.domain.model.commands.*;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import org.upc.fitwise.plan.domain.exceptions.FitwisePlanAlreadyExistsException;
import org.upc.fitwise.plan.domain.exceptions.FitwisePlanNotFoundException;
import org.upc.fitwise.plan.domain.exceptions.NoValidTagsFoundException;
import org.upc.fitwise.plan.domain.exceptions.UnauthorizedPlanAccessException;
import org.upc.fitwise.plan.domain.exceptions.WorkoutAlreadyAssociatedException;
import org.upc.fitwise.plan.domain.exceptions.WorkoutNotFoundException;
import org.upc.fitwise.plan.domain.exceptions.WorkoutNotAssociatedException;
import org.upc.fitwise.plan.domain.exceptions.DietNotFoundException;
import org.upc.fitwise.plan.domain.exceptions.DietAlreadyAssociatedException;
import org.upc.fitwise.plan.domain.exceptions.DietNotAssociatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FitwisePlanCommandServiceImpl implements FitwisePlanCommandService {

    private final FitwisePlanRepository fitwisePlanRepository;
    private final PlanTagRepository planTagRepository;
    private final WorkoutRepository workoutRepository;
    private final DietRepository dietRepository;
    private final ExternalIamService externalIamService;

    public FitwisePlanCommandServiceImpl(FitwisePlanRepository fitwisePlanRepository, PlanTagRepository planTagRepository, WorkoutRepository workoutRepository, DietRepository dietRepository, ExternalIamService externalIamService){
        this.fitwisePlanRepository = fitwisePlanRepository;
        this.planTagRepository = planTagRepository;
        this.workoutRepository = workoutRepository;
        this.dietRepository = dietRepository;
        this.externalIamService = externalIamService;
    }


    @Override
    public Long handle(CreateFitwisePlanCommand command) {
        if (fitwisePlanRepository.existsByTitle(command.title())) {
            throw new FitwisePlanAlreadyExistsException(command.title());
        }

        List<PlanTag> tagsToAssociate = command.tagNames().stream()
                .map(planTagRepository::findByTitle)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (tagsToAssociate.isEmpty() && !command.tagNames().isEmpty()) {
            throw new NoValidTagsFoundException("No valid tags found to associate with the plan for the provided tag names.");
        }

        FitwisePlan newPlan = new FitwisePlan(command.title(), command.note());
        newPlan.setTags(tagsToAssociate);

        fitwisePlanRepository.save(newPlan);

        return newPlan.getId();
    }


    @Override
    public void handle(AddWorkoutToFitwisePlanCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedPlanAccessException("User not found or unauthorized for this operation."));

        FitwisePlan fitwisePlan = fitwisePlanRepository.findById(command.fitwisePlanId())
                .orElseThrow(() -> new FitwisePlanNotFoundException(command.fitwisePlanId()));

        if (!userId.equals(fitwisePlan.getUserId())) {
            throw new UnauthorizedPlanAccessException("Add not permitted: Fitwise Plan belongs to a different user");
        }
        Workout workoutToAdd  = workoutRepository.findById(command.workoutId())
                .orElseThrow(() -> new WorkoutNotFoundException(command.workoutId()));

        if (Objects.equals(fitwisePlan.getWorkout(), workoutToAdd)) {
            throw new WorkoutAlreadyAssociatedException();
        }

        fitwisePlan.assignWorkout(workoutToAdd);
        fitwisePlanRepository.save(fitwisePlan);
        System.out.println("Workout added to fitwisePlan");
    }

    @Override
    public void handle(RemoveWorkoutToFitwisePlanCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedPlanAccessException("User not found or unauthorized for this operation."));

        FitwisePlan fitwisePlan = fitwisePlanRepository.findById(command.fitwisePlanId())
                .orElseThrow(() -> new FitwisePlanNotFoundException(command.fitwisePlanId()));
        if (!userId.equals(fitwisePlan.getUserId())) {
            throw new UnauthorizedPlanAccessException("Remove not permitted: Fitwise Plan belongs to a different user");
        }
        Workout workoutToRemove  = workoutRepository.findById(command.workoutId())
                .orElseThrow(() -> new WorkoutNotFoundException(command.workoutId()));

        if (!Objects.equals(fitwisePlan.getWorkout(), workoutToRemove)) {
            throw new WorkoutNotAssociatedException();
        }

        fitwisePlan.removeWorkout();
        fitwisePlanRepository.save(fitwisePlan);
        System.out.println("Workout removed from fitwisePlan");
    }


    @Override
    public void handle(AddDietToFitwisePlanCommand command) {

        FitwisePlan fitwisePlan = fitwisePlanRepository.findById(command.fitwisePlanId())
                .orElseThrow(() -> new FitwisePlanNotFoundException(command.fitwisePlanId()));

        Diet dietToAdd  = dietRepository.findById(command.dietId())
                .orElseThrow(() -> new DietNotFoundException(command.dietId()));


        if (Objects.equals(fitwisePlan.getDiet(), dietToAdd)) {
            throw new DietAlreadyAssociatedException();
        }

        fitwisePlan.assignDiet(dietToAdd);
        fitwisePlanRepository.save(fitwisePlan);
        System.out.println("Diet added to fitwisePlan");
    }

    @Override
    public void handle(RemoveDietToFitwisePlanCommand command) {

        FitwisePlan fitwisePlan = fitwisePlanRepository.findById(command.fitwisePlanId())
                .orElseThrow(() -> new FitwisePlanNotFoundException(command.fitwisePlanId()));

        Diet dietToRemove  = dietRepository.findById(command.dietId())
                .orElseThrow(() -> new DietNotFoundException(command.dietId()));

        if (!Objects.equals(fitwisePlan.getDiet(), dietToRemove)) {
            throw new DietNotAssociatedException();
        }

        fitwisePlan.removeDiet();
        fitwisePlanRepository.save(fitwisePlan);
        System.out.println("Diet removed from fitwisePlan");
    }
}