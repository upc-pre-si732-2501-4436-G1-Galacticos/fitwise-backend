package org.upc.fitwise.plan.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal extends AuditableAbstractAggregateRoot<Meal>{
    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "meals", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonBackReference
    private List<Diet> diets;

    public Meal() {
        this.diets = new ArrayList<>();
    }

    public Meal(String title, String description) {
        this.title = title;
        this.description = description;
        this.diets = new ArrayList<>();
    }



    public void addDiet(Diet diet) {
        if (!this.diets.contains(diet)) {
            this.diets.add(diet);
        }
    }
}