package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;

public class DietResourceFromEntityAssembler {
    public static DietResource toResourceFromEntity(Diet entity) {
        return new DietResource(entity.getId(),entity.getTitle(),entity.getNote());
    }
}
