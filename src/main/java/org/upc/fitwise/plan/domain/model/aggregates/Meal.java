package org.upc.fitwise.plan.domain.model.aggregates;


import jakarta.persistence.Entity;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


@Entity
public class Meal extends AuditableAbstractAggregateRoot<Meal>{
    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private Long userId;

    public Meal() {
    }

    public Meal updateInformation(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }


    public Meal(String title, String description,Long userId) {
        this.title = title;
        this.description = description;
        this.userId=userId;
    }

}