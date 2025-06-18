package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class PlanSubscription extends AuditableAbstractAggregateRoot<PlanSubscription> {

    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitwise_plan_id", nullable = false)
    private FitwisePlan fitwisePlan;

    @Getter
    private Integer currentWorkoutId;

    @Getter
    private Integer currentDietId;

    @Getter
    @Column(nullable = false)
    private LocalDate subscriptionStartDate;

    @Getter
    @Column(nullable = false)
    private LocalDate endDate;

    @Getter
    @Column(nullable = false)
    private boolean isActive;

    @Setter
    @Getter
    private String notes;

    public PlanSubscription(Long userId, FitwisePlan fitwisePlan, LocalDate subscriptionStartDate, LocalDate endDate) {
        this.userId = userId;
        this.fitwisePlan = fitwisePlan;
        this.subscriptionStartDate = subscriptionStartDate;
        this.endDate = endDate;
        this.isActive = true;
    }

    public static PlanSubscription createWithFitwisePlanId(Long userId,
                                                           Long fitwisePlanId,
                                                           LocalDate subscriptionStartDate,
                                                           LocalDate endDate,
                                                           String notes,
                                                           FitwisePlanRepository fitwisePlanRepository) {
        var fitwisePlan = fitwisePlanRepository.findById(fitwisePlanId)
                .orElseThrow(() -> new IllegalArgumentException("El plan con ID " + fitwisePlanId + " no existe."));
        var planSubscription = new PlanSubscription(userId, fitwisePlan, subscriptionStartDate, endDate);
        planSubscription.setNotes(notes);
        return planSubscription;
    }


    public void cancel() {
        this.isActive = false;
    }

    public void renew(LocalDate newEndDate) {
        if (newEndDate.isAfter(this.endDate)) {
            this.endDate = newEndDate;
            this.isActive = true;
        }
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(this.endDate);
    }

    public void deactivateIfExpired() {
        if (isExpired()) {
            this.isActive = false;
        }
    }


}
