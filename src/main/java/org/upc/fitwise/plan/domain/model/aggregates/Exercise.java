package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise extends AuditableAbstractAggregateRoot<Exercise> {

    @Getter
    private String title;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "exercises", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Workout> workouts;


    public Exercise() {
        this.workouts = new ArrayList<>();
    }


    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

}