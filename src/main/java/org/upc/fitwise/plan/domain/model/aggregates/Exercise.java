package org.upc.fitwise.plan.domain.model.aggregates;


import jakarta.persistence.*;
import lombok.Getter;

import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Exercise extends AuditableAbstractAggregateRoot<Exercise> {

    @Getter
    private String title;

    @Getter
    private String description;


    @Getter
    private Long userId;


    public Exercise() {
    }


    public Exercise updateInformation(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }


    public Exercise(String title, String description,Long userId) {
        this.title = title;
        this.description = description;
        this.userId=userId;
    }


}