package org.upc.fitwise.plan.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Entity
public class PlanTag extends AuditableAbstractAggregateRoot<PlanTag> {
    @Getter
    private String title;

    @Getter
    @ManyToMany(mappedBy = "tags")
    private List<FitwisePlan> plans;
    public PlanTag() {}



}
