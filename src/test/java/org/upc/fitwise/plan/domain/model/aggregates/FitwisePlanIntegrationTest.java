package org.upc.fitwise.plan.domain.model.aggregates;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FitwisePlanIntegrationTest {

    @Autowired
    private FitwisePlanRepository fitwisePlanRepository; // Asumiendo que tienes un repositorio de Spring Data JPA

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private PlanTagRepository planTagRepository;

    @Test
    @DisplayName("Should create and retrieve a FitwisePlan with its related entities")
    void shouldCreateAndRetrieveFitwisePlan() {
        // Crear entidades relacionadas
        Diet diet = new Diet("Keto", "Low carb");
        dietRepository.save(diet);
        Workout workout = new Workout("Full Body", "Strength training");
        workoutRepository.save(workout);
        PlanTag tag1 = new PlanTag("Beginner");
        planTagRepository.save(tag1);
        PlanTag tag2 = new PlanTag("Strength");
        planTagRepository.save(tag2);

        // Crear FitwisePlan
        FitwisePlan plan = new FitwisePlan("My Plan", "Description");
        plan.setDiet(diet);
        plan.setWorkout(workout);
        plan.addPlanTag(tag1);
        plan.addPlanTag(tag2);

        // Persistir el plan
        fitwisePlanRepository.save(plan);

        // Recuperar el plan
        FitwisePlan retrievedPlan = fitwisePlanRepository.findById(plan.getId()).orElse(null);

        // Verificar
        assertNotNull(retrievedPlan);
        assertEquals("My Plan", retrievedPlan.getTitle());
        assertEquals("Description", retrievedPlan.getDescription());
        assertEquals(diet.getId(), retrievedPlan.getDiet().getId());
        assertEquals(workout.getId(), retrievedPlan.getWorkout().getId());
        assertEquals(2, retrievedPlan.getTags().size());
        assertTrue(retrievedPlan.getTags().contains(tag1));
        assertTrue(retrievedPlan.getTags().contains(tag2));
    }

    @Test
    @DisplayName("Should update a FitwisePlan and its related entities")
    void shouldUpdateFitwisePlan() {
        // Crear entidades relacionadas iniciales
        Diet diet = new Diet("Keto", "Low carb");
        dietRepository.save(diet);
        Workout workout = new Workout("Full Body", "Strength training");
        workoutRepository.save(workout);
        PlanTag tag1 = new PlanTag("Beginner");
        planTagRepository.save(tag1);
        PlanTag tag2 = new PlanTag("Strength");
        planTagRepository.save(tag2);

        // Crear FitwisePlan inicial
        FitwisePlan plan = new FitwisePlan("My Plan", "Description");
        plan.setDiet(diet);
        plan.setWorkout(workout);
        plan.addPlanTag(tag1);
        plan.addPlanTag(tag2);
        fitwisePlanRepository.save(plan);

        // Recuperar el plan para la actualización
        FitwisePlan planToUpdate = fitwisePlanRepository.findById(plan.getId()).orElse(null);
        assertNotNull(planToUpdate);


        // Crear una nueva dieta y actualizar la existente.
        Diet updatedDiet = new Diet("Paleo", "High protein");
        dietRepository.save(updatedDiet);
        planToUpdate.setDiet(updatedDiet);

        // Crear un nuevo workout y actualiar el existente
        Workout updatedWorkout = new Workout("Upper Body", "Strength training for upper body");
        workoutRepository.save(updatedWorkout);
        planToUpdate.setWorkout(updatedWorkout);

        // Agregar un nuevo tag y eliminar uno existente
        PlanTag tag3 = new PlanTag("Advanced");
        planTagRepository.save(tag3);
        planToUpdate.addPlanTag(tag3);
        planToUpdate.removePlanTag(tag1);

        // Guardar los cambios
        fitwisePlanRepository.save(planToUpdate);

        // Recuperar el plan actualizado para verificar
        FitwisePlan retrievedPlan = fitwisePlanRepository.findById(planToUpdate.getId()).orElse(null);
        assertNotNull(retrievedPlan);

        // Verificar los cambios
        assertEquals(updatedDiet.getId(), retrievedPlan.getDiet().getId()); // Verifica que la dieta se actualizó
        assertEquals(updatedWorkout.getId(), retrievedPlan.getWorkout().getId()); // Verifica el workout se actualizó
        assertEquals(2, retrievedPlan.getTags().size()); // Verifica el número de tags
        assertTrue(retrievedPlan.getTags().contains(tag3)); // Verifica que el nuevo tag se agregó
        assertFalse(retrievedPlan.getTags().contains(tag1)); // Verifica que el tag anterior se eliminó
    }
}

