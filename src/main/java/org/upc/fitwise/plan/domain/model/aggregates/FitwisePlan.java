package org.upc.fitwise.plan.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class FitwisePlan extends AuditableAbstractAggregateRoot<FitwisePlan> {

    @Getter
    @Setter
    private Long dietId;

    @Getter
    @Setter
    private Long workoutId;

    @Getter
    private String title;

    @Getter
    private String note;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "fitwise_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_tag_id")
    )
    private List<PlanTag> tags;

    public FitwisePlan() {}
    public FitwisePlan( String title, String note) {
        this.title = title;
        this.note = note;
    }

    public List<String> getTagNamesAsArray() {
        if (this.tags != null && !this.tags.isEmpty()) {
            return this.tags.stream()
                    .map(PlanTag::getTitle)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
