package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Workout extends AuditableAbstractAggregateRoot<Workout> {

    @Getter
    private String title;
    @Getter
    private String description;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises;

    @Getter
    private Long userId;

    public Workout() {
        this.exercises = new ArrayList<>();
    }
    public Workout(String title, String description,Long userId) {
        this.title = title;
        this.description = description;
        this.exercises = new ArrayList<>();
        this.userId = userId;
    }

    public Workout updateInformation(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }


    public void addExerciseToWorkout(Exercise exercise) {

        if (!this.exercises.contains(exercise)) {
            this.exercises.add(exercise);
        }
    }

    public void removeExerciseToWorkout(Exercise exercise) {
        if (this.exercises != null) {
            this.exercises.remove(exercise);
        }
    }





}