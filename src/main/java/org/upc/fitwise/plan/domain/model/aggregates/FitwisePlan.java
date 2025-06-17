package org.upc.fitwise.plan.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.profiles.domain.model.aggregates.Profile;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class FitwisePlan extends AuditableAbstractAggregateRoot<FitwisePlan> {

    @Getter
    @ManyToOne
    @JoinColumn(name = "diet_id")
    private Diet diet;

    @Getter
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Setter
    @Getter
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "plan_plan_tag",
        joinColumns = @JoinColumn(name = "fitwise_plan_id"),
        inverseJoinColumns = @JoinColumn(name = "plan_tag_id")
    )
    private List<PlanTag> tags = new ArrayList<>();

    @Getter
    @Setter
    private Long userId;

    @Getter
    private String title;

    @Getter
    private String description;




    public FitwisePlan() {}
    public FitwisePlan( String title, String description) {
        this.title = title;
        this.description = description;
        this.tags = new ArrayList<>();

    }

    public List<String> getTagNamesAsArray() {
        if (this.tags != null && !this.tags.isEmpty()) {
            return this.tags.stream()
                    .map(PlanTag::getTitle)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void assignWorkout(Workout workout) {
        this.workout = workout;
    }

    public void removeWorkout() {
        this.workout = null;
    }

    public void assignDiet(Diet diet) {
        this.diet = diet;
    }

    public void removeDiet() {
        this.diet = null;
    }


    public void addPlanTag(PlanTag tag) {

        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }

    public void removePlanTag(PlanTag tag) {
        if (this.tags != null) {
            this.tags.remove(tag);
        }
    }

}
