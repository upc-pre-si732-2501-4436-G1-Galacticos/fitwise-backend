package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
public class PlanSubscription extends AuditableAbstractAggregateRoot<PlanSubscription> {

    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitwise_plan_id", nullable = false)
    private FitwisePlan fitwisePlan;

    @Getter
    @Column(name = "current_workout_id")
    private Integer currentWorkoutId;

    @Getter
    @Column(name = "current_diet_id")
    private Integer currentDietId;

    @Getter
    private LocalDate subscriptionStartDate;

    @Getter
    private LocalDate endDate;

    @Getter
    private Boolean isActive;

    @Getter
    private String notes;
}
