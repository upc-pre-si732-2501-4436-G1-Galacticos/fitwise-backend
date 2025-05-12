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
import java.util.stream.Collectors;


@Entity
public class Workout extends AuditableAbstractAggregateRoot<Workout> {

    @Getter
    private String title;
    @Getter
    private String note;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises;

    public Workout() {
        this.exercises = new ArrayList<>();
    }


    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }





}