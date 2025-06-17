package org.upc.fitwise.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.plan.domain.model.aggregates.Meal;
import org.upc.fitwise.plan.domain.model.aggregates.PlanTag;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ActivityLevel extends AuditableAbstractAggregateRoot<ActivityLevel> {


    @Getter
    private String levelName;
    @Getter
    private BigDecimal score;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "activity_level_plan_tag",
            joinColumns = @JoinColumn(name = "activity_level_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_tag_id")
    )
    private List<PlanTag> planTags;

    // Constructor para JPA
    public ActivityLevel() {
        this.planTags = new ArrayList<>();
    }

    // Constructor para facilitar el seeding
    public ActivityLevel(String levelName, BigDecimal score) {
        this.levelName = levelName;
        this.score = score;
        this.planTags = new ArrayList<>();
    }

    // Constructor para cuando se crean con tags iniciales
    public ActivityLevel(String levelName, BigDecimal score, List<PlanTag> planTags) {
        this.levelName = levelName;
        this.score = score;
        this.planTags = new ArrayList<>(planTags);
    }

    public List<String> getTagNamesActivityLevel() {
        if (this.planTags != null && !this.planTags.isEmpty()) {
            return this.planTags.stream()
                    .map(PlanTag::getTitle)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


}
