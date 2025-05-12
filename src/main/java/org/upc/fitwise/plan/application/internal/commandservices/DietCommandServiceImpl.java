package org.upc.fitwise.plan.application.internal.commandservices;


import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.commands.AddMealToDietCommand;
import org.upc.fitwise.plan.domain.services.DietCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.MealRepository;


@Service
public class DietCommandServiceImpl implements DietCommandService {

    private final DietRepository dietRepository;
    private final MealRepository mealRepository;


    public DietCommandServiceImpl(DietRepository dietRepository,MealRepository mealRepository){
        this.dietRepository=dietRepository;
        this.mealRepository=mealRepository;
    }


    @Override
    public void handle(AddMealToDietCommand command) {
        Diet diet = dietRepository.findById(command.dietId())
                .orElseThrow(() -> new IllegalArgumentException("Diet does not exist"));

        Meal mealToAdd = mealRepository.findById(command.mealId())
                .orElseThrow(() -> new IllegalArgumentException("Meal does not exist"));

        if (diet.getMeals().contains(mealToAdd)) {
            throw new IllegalArgumentException("This meal is already added to the diet.");
        }

        try {
            diet.addMeal(mealToAdd);
            dietRepository.save(diet);
            System.out.println("Meal added to diet");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding meal to diet: " + e.getMessage());
        }
    }






}