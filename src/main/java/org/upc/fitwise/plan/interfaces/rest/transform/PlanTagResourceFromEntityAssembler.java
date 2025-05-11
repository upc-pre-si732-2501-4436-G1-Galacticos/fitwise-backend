package org.upc.fitwise.plan.interfaces.rest.transform;


import org.upc.fitwise.plan.domain.model.aggregates.Diet;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.plan.interfaces.rest.resources.DietResource;
import org.upc.fitwise.plan.interfaces.rest.resources.PlanTagResource;

public class PlanTagResourceFromEntityAssembler {
    public static PlanTagResource toResourceFromEntity(PlanTag entity) {
        return new PlanTagResource(entity.getId(),entity.getTitle());
    }
}
