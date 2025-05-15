package org.upc.fitwise.profiles.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class ActivityLevel extends AuditableAbstractAggregateRoot<ActivityLevel> {

    @OneToMany(mappedBy = "activityLevel")
    private List<Profile> profiles;

    @Getter
    private String levelName;
    @Getter
    private BigDecimal score;

}
