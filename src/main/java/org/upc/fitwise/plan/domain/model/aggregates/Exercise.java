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
public class Exercise extends AuditableAbstractAggregateRoot<Exercise> {

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "exercises", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonBackReference
    private List<Workout> workouts;


    public Exercise() {
        this.workouts = new ArrayList<>();
    }

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
        this.workouts = new ArrayList<>();
    }


    public void addWorkout(Workout workout) {
        if (!this.workouts.contains(workout)) {
            this.workouts.add(workout);
        }
    }
}