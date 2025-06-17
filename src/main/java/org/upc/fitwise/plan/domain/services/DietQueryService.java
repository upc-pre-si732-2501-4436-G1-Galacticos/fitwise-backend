package org.upc.fitwise.plan.domain.services;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.queries.*;

import java.util.List;
import java.util.Optional;


public interface DietQueryService {
    List<Diet> handle(GetAllDietsQuery query);
    Optional<Diet> handle(GetDietByIdQuery query);
    List<Diet> handle(GetAllDietsByUserIdQuery query);
}
