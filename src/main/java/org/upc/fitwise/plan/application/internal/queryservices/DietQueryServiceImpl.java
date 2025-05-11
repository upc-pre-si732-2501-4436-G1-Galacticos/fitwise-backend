package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.queries.GetAllDietsQuery;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;


import java.util.List;

@Service
public class DietQueryServiceImpl implements DietQueryService {

    private final DietRepository dietRepository;

    public DietQueryServiceImpl(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    @Override
    public List<Diet> handle(GetAllDietsQuery query) {
        return dietRepository.findAll();
    }


}
