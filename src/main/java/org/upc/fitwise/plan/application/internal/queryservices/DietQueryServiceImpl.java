package org.upc.fitwise.plan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.queries.*;
import org.upc.fitwise.plan.domain.services.DietQueryService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;


import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Diet> handle(GetDietByIdQuery query) {
        return dietRepository.findById(query.dietId());
    }

    @Override
    public List<Diet> handle(GetAllDietsByUserIdQuery query) {
        return dietRepository.findAllByUserId(query.userId());
    }

}
