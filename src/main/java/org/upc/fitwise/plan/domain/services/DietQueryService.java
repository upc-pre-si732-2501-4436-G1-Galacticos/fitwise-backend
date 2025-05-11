package org.upc.fitwise.plan.domain.services;




import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsQuery;

import java.util.List;


public interface DietQueryService {
    List<Diet> handle(GetAllDietsQuery query);

}
