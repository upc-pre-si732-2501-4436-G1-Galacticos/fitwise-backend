package org.upc.fitwise.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Goal extends AuditableAbstractAggregateRoot<Goal> {

    @Getter
    private String goalName;
    @Getter
    private BigDecimal score;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "goal_plan_tag",
            joinColumns = @JoinColumn(name = "goal_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_tag_id")
    )
    private List<PlanTag> planTags;

    public Goal() {
        this.planTags = new ArrayList<>();
    }

    public Goal(String goalName, BigDecimal score) {
        this.goalName = goalName;
        this.score = score;
        this.planTags = new ArrayList<>();
    }

    public Goal(String goalName, BigDecimal score, List<PlanTag> planTags) {
        this.goalName = goalName;
        this.score = score;
        this.planTags = new ArrayList<>(planTags);
    }

    public List<String> getTagNamesGoal() {
        if (this.planTags != null && !this.planTags.isEmpty()) {
            return this.planTags.stream()
                    .map(PlanTag::getTitle)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
