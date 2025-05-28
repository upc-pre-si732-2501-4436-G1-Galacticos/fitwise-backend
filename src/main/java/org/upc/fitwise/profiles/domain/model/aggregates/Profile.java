package org.upc.fitwise.profiles.domain.model.aggregates;

import lombok.Getter;
import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.plan.domain.model.aggregates.FitwisePlan;
import org.upc.fitwise.profiles.domain.model.valueobjects.PersonName;
import  org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "activity_level_id", nullable = false)
    private ActivityLevel activityLevel;

    @Getter
    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @Getter
    @OneToMany(mappedBy = "fitwise_plans", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FitwisePlan> fitwisePlan;



    public Profile(String firstName, String lastName, String gender, BigDecimal height, BigDecimal weight, ActivityLevel activityLevel, Goal goal,Long userId) {
        this.name = new PersonName(firstName, lastName);
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
        this.userId=userId;
        this.score =calculateScore(height, weight,activityLevel, goal);
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
        this.score =calculateScore(height, weight,activityLevel, goal);
        return this;
    }
    public BigDecimal calculateScore(BigDecimal height, BigDecimal weight, ActivityLevel activityLevel, Goal goal) {
        BigDecimal heightSquared = height.multiply(height);

        BigDecimal imc = weight.divide(heightSquared, 2, RoundingMode.HALF_UP);

        // IMC score
        BigDecimal imcScore;
        if (imc.compareTo(new BigDecimal("18.5")) < 0) imcScore = new BigDecimal("2");
        else if (imc.compareTo(new BigDecimal("25")) < 0) imcScore = new BigDecimal("10");
        else if (imc.compareTo(new BigDecimal("30")) < 0) imcScore = new BigDecimal("6");
        else imcScore = new BigDecimal("3");

        BigDecimal activityScore = activityLevel.getScore();
        BigDecimal goalScore = goal.getScore();

        BigDecimal finalScore =
                imcScore.multiply(new BigDecimal("0.5"))
                        .add(activityScore.multiply(new BigDecimal("0.3")))
                        .add(goalScore.multiply(new BigDecimal("0.2")));
        return finalScore.setScale(2, RoundingMode.HALF_UP);
    }



}
