package org.upc.fitwise.plan.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.domain.model.aggregates.Workout;
import org.upc.fitwise.plan.domain.model.commands.AddDietToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.AddWorkoutToFitwisePlanCommand;
import org.upc.fitwise.plan.domain.model.commands.CreateFitwisePlanCommand;
import org.upc.fitwise.plan.domain.services.FitwisePlanCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class FitwisePlanCommandServiceImpl implements FitwisePlanCommandService {

    private final FitwisePlanRepository fitwisePlanRepository;
    private final PlanTagRepository planTagRepository;
    private final WorkoutRepository workoutRepository;
    private final DietRepository dietRepository;
    public FitwisePlanCommandServiceImpl(FitwisePlanRepository fitwisePlanRepository,PlanTagRepository planTagRepository,WorkoutRepository workoutRepository,DietRepository dietRepository){
        this.fitwisePlanRepository=fitwisePlanRepository;
        this.planTagRepository=planTagRepository;
        this.workoutRepository=workoutRepository;
        this.dietRepository=dietRepository;
    }


    @Override
    public Long handle(CreateFitwisePlanCommand command) {
        FitwisePlan newPlan = new FitwisePlan(command.title(),command.note());
        if (fitwisePlanRepository.existsByTitle(command.title())) {
            throw new IllegalArgumentException("Plan already exists");
        }
        List<PlanTag> tagsToAssociate = new ArrayList<>();

        for (String tagName : command.tagNames()) {
            planTagRepository.findByTitle(tagName).ifPresent(tagsToAssociate::add);
        }
       try {
           if (tagsToAssociate.isEmpty()) {
               throw new IllegalArgumentException("No valid tags found to associate with the plan.");
           }

            newPlan.setTags(tagsToAssociate);
            fitwisePlanRepository.save(newPlan);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving FitwisePlan: " + e.getMessage());
        }
        return newPlan.getId();
    }

    @Override
    public void handle(AddWorkoutToFitwisePlanCommand command) {
        if (!fitwisePlanRepository.existsById(command.fitwisePlanId())) {
            throw new IllegalArgumentException("FitwisePlan does not exist");
        }
        if (fitwisePlanRepository.existsByWorkoutId(command.workoutId())) {
            throw new IllegalArgumentException("The workout is already associated with the plan");
        }
        try {
            fitwisePlanRepository.findById(command.fitwisePlanId()).map(fitwisePlan -> {
                Workout workout = workoutRepository.findById(command.workoutId())
                        .orElseThrow(() -> new IllegalArgumentException("Workout does not exist"));
                fitwisePlan.setWorkout(workout);
                fitwisePlanRepository.save(fitwisePlan);
                System.out.println("Workout added to fitwisePlan");
                return fitwisePlan;
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding Workout to fitwisePlan: " + e.getMessage());
        }


    }

    @Override
    public void handle(AddDietToFitwisePlanCommand command) {
        if (!fitwisePlanRepository.existsById(command.fitwisePlanId())) {
            throw new IllegalArgumentException("FitwisePlan does not exist");
        }
        if (fitwisePlanRepository.existsByDietId(command.dietId())) {
            throw new IllegalArgumentException("The Diet is already associated with the plan");
        }
        try {
            fitwisePlanRepository.findById(command.fitwisePlanId()).map(fitwisePlan -> {
                Diet diet = dietRepository.findById(command.dietId())
                        .orElseThrow(() -> new IllegalArgumentException("Diet does not exist"));
                fitwisePlan.setDiet(diet);
                fitwisePlanRepository.save(fitwisePlan);
                System.out.println("Diet added to fitwisePlan");
                return fitwisePlan;
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding Diet to fitwisePlan: " + e.getMessage());
        }


    }






}