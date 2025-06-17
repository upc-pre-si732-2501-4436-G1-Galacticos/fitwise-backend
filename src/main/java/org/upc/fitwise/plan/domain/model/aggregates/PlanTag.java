package org.upc.fitwise.plan.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PlanTag extends AuditableAbstractAggregateRoot<PlanTag> {


    @Getter
    private String title;

    public PlanTag(String title) {
        this.title = title;
    }
    public PlanTag() {}



}
