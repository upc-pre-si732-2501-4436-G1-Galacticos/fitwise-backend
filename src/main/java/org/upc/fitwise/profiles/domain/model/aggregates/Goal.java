package org.upc.fitwise.profiles.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Goal extends AuditableAbstractAggregateRoot<Goal> {

    @OneToMany(mappedBy = "goal")
    private List<Profile> profiles;
    @Getter
    private String goalName;
    @Getter
    private BigDecimal score;
}
