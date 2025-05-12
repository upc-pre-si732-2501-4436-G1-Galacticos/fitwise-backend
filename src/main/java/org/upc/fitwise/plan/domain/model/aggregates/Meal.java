package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Meal extends AuditableAbstractAggregateRoot<Meal>{
    @Getter
    private String title;
}