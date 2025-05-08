package org.upc.fitwise.profiles.domain.model.aggregates;

import lombok.Getter;
import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.profiles.domain.model.valueobjects.PersonName;
import  org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Embedded
    @Getter
    private PersonName name;
    @Getter
    private String gender;
    @Getter
    private BigDecimal height;
    @Getter
    private BigDecimal weight;
    @Getter
    private BigDecimal score;
    @Getter
    //@MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private Long user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "activity_level_id", nullable = false)
    private ActivityLevel activityLevel;

    @Getter
    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;



    public Profile(String firstName, String lastName, String gender, BigDecimal height, BigDecimal weight, ActivityLevel activityLevel, Goal goal,Long userId) {
        this.name = new PersonName(firstName, lastName);
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
        this.user=userId;
    }

    public Profile() {

    }

    public String getFullName() { return name.getFullName(); }
    public Profile updateInformation(String firstName, String lastName, String gender, BigDecimal height, BigDecimal weight, ActivityLevel activityLevel, Goal goal) {
        this.name = new PersonName(firstName, lastName);
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
        return this;
    }




}
