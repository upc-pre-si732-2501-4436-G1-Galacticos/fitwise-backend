package org.upc.fitwise.plan.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.exceptions.MealAlreadyExistsException;
import org.upc.fitwise.plan.domain.exceptions.MealNotFoundException;
import org.upc.fitwise.plan.domain.exceptions.UnauthorizedMealAccessException;
import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.commands.CreateMealCommand;
import org.upc.fitwise.plan.domain.model.commands.UpdateMealCommand;
import org.upc.fitwise.plan.domain.services.MealCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.MealRepository;

import java.util.Optional;

@Service
public class MealCommandServiceImpl implements MealCommandService {
    private final MealRepository mealRepository;
    private final ExternalIamService externalIamService;
    public MealCommandServiceImpl(MealRepository mealRepository, ExternalIamService externalIamService) {
        this.mealRepository= mealRepository;
        this.externalIamService= externalIamService;
    }

    @Override
    public Long handle(CreateMealCommand command) {
        // Asumiendo que fetchUserIdByEmail retorna Optional<Long>
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedMealAccessException("Unable to create meal because user does not exist or is unauthorized.")); // Usa excepción de dominio

        if (mealRepository.existsByTitle(command.title())) {
            throw new MealAlreadyExistsException(command.title()); // Usa MealAlreadyExistsException
        }

        var meal = new Meal(command.title(), command.description(), userId);

        // Eliminar el try-catch genérico. Deja que las excepciones de persistencia (si ocurren)
        // se propaguen y sean manejadas por el @ControllerAdvice.
        mealRepository.save(meal);
        return meal.getId();
    }


    @Override
    public Optional<Meal> handle(UpdateMealCommand command) {
        // Asumiendo que fetchUserIdByEmail retorna Optional<Long>
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedMealAccessException("Unable to update meal because user does not exist or is unauthorized.")); // Usa excepción de dominio

        if (mealRepository.existsByTitleAndIdIsNot(command.title(), command.mealId())) {
            throw new MealAlreadyExistsException(command.title()); // Usa MealAlreadyExistsException
        }

        var result = mealRepository.findById(command.mealId());
        if (result.isEmpty()) {
            throw new MealNotFoundException(command.mealId()); // Usa MealNotFoundException
        }

        var mealToUpdate = result.get();
        if (!userId.equals(mealToUpdate.getUserId())) {
            throw new UnauthorizedMealAccessException("Update not permitted: meal belongs to a different user"); // Usa UnauthorizedMealAccessException
        }

        // Eliminar el try-catch genérico.
        var updatedMeal = mealRepository.save(mealToUpdate.updateInformation(command.title(), command.description()));
        return Optional.of(updatedMeal);
    }


}
