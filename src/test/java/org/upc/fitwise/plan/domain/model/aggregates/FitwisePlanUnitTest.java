package org.upc.fitwise.plan.domain.model.aggregates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FitwisePlanUnitTest {

    @Test
    @DisplayName("Should create a FitwisePlan with correct title and note")
    void shouldCreateFitwisePlan() {
        String title = "My Single Plan";
        String note = "A plan with one diet and one workout";
        FitwisePlan plan = new FitwisePlan(title, note);
        assertEquals(title, plan.getTitle());
        assertEquals(note, plan.getDescription());
        assertNotNull(plan.getTags());
        assertTrue(plan.getTags().isEmpty());
        assertNull(plan.getDiet());
        assertNull(plan.getWorkout());
    }

    @Test
    @DisplayName("Should add tags to a FitwisePlan")
    void shouldAddTagsToFitwisePlan() {
        FitwisePlan plan = new FitwisePlan("Tagged Plan", "...");
        PlanTag tag1 = new PlanTag("Single Diet");
        PlanTag tag2 = new PlanTag("Single Workout");
        plan.addPlanTag(tag1);
        plan.addPlanTag(tag2);
        assertEquals(2, plan.getTags().size());
        assertTrue(plan.getTags().contains(tag1));
        assertTrue(plan.getTags().contains(tag2));
    }

    @Test
    @DisplayName("Should set a Diet for the FitwisePlan")
    void shouldSetDietForFitwisePlan() {
        FitwisePlan plan = new FitwisePlan("Diet Plan", "...");
        Diet keto = new Diet("Keto", "Low carb");
        plan.setDiet(keto);
        assertEquals(keto, plan.getDiet());
    }

    @Test
    @DisplayName("Should update the Diet for the FitwisePlan")
    void shouldUpdateDietForFitwisePlan() {
        FitwisePlan plan = new FitwisePlan("Updated Diet Plan", "...");
        Diet keto = new Diet("Keto", "Low carb");
        Diet paleo = new Diet("Paleo", "Ancestral diet");
        plan.setDiet(keto);
        assertEquals(keto, plan.getDiet());
        plan.setDiet(paleo);
        assertEquals(paleo, plan.getDiet());
    }

    @Test
    @DisplayName("Should set a Workout for the FitwisePlan")
    void shouldSetWorkoutForFitwisePlan() {
        FitwisePlan plan = new FitwisePlan("Workout Plan", "...");
        Workout fullBody = new Workout("Full Body A", "...");
        plan.setWorkout(fullBody);
        assertEquals(fullBody, plan.getWorkout());
    }

    @Test
    @DisplayName("Should update the Workout for the FitwisePlan")
    void shouldUpdateWorkoutForFitwisePlan() {
        FitwisePlan plan = new FitwisePlan("Updated Workout Plan", "...");
        Workout fullBodyA = new Workout("Full Body A", "...");
        Workout upperBody = new Workout("Upper Body", "...");
        plan.setWorkout(fullBodyA);
        assertEquals(fullBodyA, plan.getWorkout());
        plan.setWorkout(upperBody);
        assertEquals(upperBody, plan.getWorkout());
    }
}