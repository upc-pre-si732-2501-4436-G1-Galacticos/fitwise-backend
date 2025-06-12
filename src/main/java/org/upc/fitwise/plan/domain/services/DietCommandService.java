package org.upc.fitwise.plan.domain.services;


import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.commands.*;

import java.util.Optional;

public interface DietCommandService {
    void handle(AddMealToDietCommand command);
    void handle(RemoveMealToDietCommand command);
    Long handle(CreateDietCommand command);
    Optional<Diet> handle(UpdateDietCommand command);
}
