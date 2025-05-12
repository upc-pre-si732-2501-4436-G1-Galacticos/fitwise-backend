package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Diet extends AuditableAbstractAggregateRoot<Diet> {

    @Getter
    private String title;
    @Getter
    private String note;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;
    public Diet() {
        this.meals = new ArrayList<>();
    }


    public void addMeal(Meal meal) {
        meals.add(meal);
    }

}