package org.upc.fitwise.shared.infrastructure.data.seeding;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import org.upc.fitwise.plan.domain.model.aggregates.*;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.DietRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.ExerciseRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.MealRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanTagRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.WorkoutRepository;

import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.entities.Role;
import org.upc.fitwise.iam.domain.model.valueobjects.Roles;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.upc.fitwise.profiles.domain.model.aggregates.ActivityLevel;
import org.upc.fitwise.profiles.domain.model.aggregates.Goal;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.ActivityLevelRepository;
import org.upc.fitwise.profiles.infrastructure.persistence.jpa.repositories.GoalRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final PlanTagRepository planTagRepository;
    private final GoalRepository goalRepository;
    private final ActivityLevelRepository activityLevelRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final FitwisePlanRepository fitwisePlanRepository;
    private final MealRepository mealRepository;
    private final DietRepository dietRepository;

    private Long adminUserId = null; // Para almacenar el ID del usuario admin

    public DatabaseSeeder(PlanTagRepository planTagRepository,
                          GoalRepository goalRepository,
                          ActivityLevelRepository activityLevelRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          ExerciseRepository exerciseRepository,
                          WorkoutRepository workoutRepository,
                          FitwisePlanRepository fitwisePlanRepository,
                          MealRepository mealRepository, // Nuevo
                          DietRepository dietRepository) { // Nuevo
        this.planTagRepository = planTagRepository;
        this.goalRepository = goalRepository;
        this.activityLevelRepository = activityLevelRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.fitwisePlanRepository = fitwisePlanRepository;
        this.mealRepository = mealRepository;
        this.dietRepository = dietRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Orden de ejecución crucial:
        // Roles -> Users (para obtener adminUserId)
        // -> PlanTags -> Goals -> ActivityLevels
        // -> Meals -> Diets
        // -> Exercises -> Workouts
        // -> FitwisePlans (depende de Workout y Diet)

        if (roleRepository.count() == 0) {
            seedRoles();
        } else {
            System.out.println("Roles already seeded. Skipping data initialization for Role.");
        }

        // Sembrar usuarios y capturar el ID del admin
        if (userRepository.count() == 0) {
            seedUsers();
        } else {
            System.out.println("Users already seeded. Skipping data initialization for User.");
            // Si ya hay usuarios, intenta encontrar el ID del admin para las siguientes semillas
            Optional<User> adminUser = userRepository.findByEmail("davidalexd1234@gmail.com"); // Email del admin
            adminUser.ifPresent(user -> this.adminUserId = user.getId());
        }

        // Si el adminUserId no se pudo encontrar o aún no se ha sembrado, no continuar con lo que depende de él
        if (adminUserId == null) {
            System.err.println("Admin user ID not found or not seeded. Cannot seed dependent entities.");
            return; // Detener la ejecución si no hay admin ID
        }

        // Sembrar entidades que no dependen del adminUserId o de otras entidades complejas
        if (planTagRepository.count() == 0) {
            seedPlanTags();
        } else {
            System.out.println("PlanTags already seeded. Skipping data initialization for PlanTag.");
        }

        if (goalRepository.count() == 0) {
            seedGoals();
        } else {
            System.out.println("Goals already seeded. Skipping data initialization for Goal.");
        }

        if (activityLevelRepository.count() == 0) {
            seedActivityLevels();
        } else {
            System.out.println("Activity Levels already seeded. Skipping data initialization for ActivityLevel.");
        }

        // Sembrar Meals (depende de userId)
        if (mealRepository.count() == 0) {
            seedMeals(adminUserId);
        } else {
            System.out.println("Meals already seeded. Skipping data initialization for Meal.");
        }

        // Sembrar Diets (depende de userId y Meals)
        if (dietRepository.count() == 0) {
            seedDiets(adminUserId);
        } else {
            System.out.println("Diets already seeded. Skipping data initialization for Diet.");
        }

        // Sembrar Exercises (depende de userId)
        if (exerciseRepository.count() == 0) {
            seedExercises(adminUserId);
        } else {
            System.out.println("Exercises already seeded. Skipping data initialization for Exercise.");
        }

        // Sembrar Workouts (depende de userId y Exercises)
        if (workoutRepository.count() == 0) {
            seedWorkouts(adminUserId);
        } else {
            System.out.println("Workouts already seeded. Skipping data initialization for Workout.");
        }

        // Sembrar FitwisePlans (depende de userId, Workouts y Diets)
        if (fitwisePlanRepository.count() == 0) {
            seedFitwisePlans(adminUserId);
        } else {
            System.out.println("FitwisePlans already seeded. Skipping data initialization for FitwisePlan.");
        }
    }

    private void seedRoles() {
        System.out.println("Seeding Roles...");
        Arrays.stream(Roles.values()).forEach(roleNameEnum -> {
            if (!roleRepository.existsByName(roleNameEnum)) {
                roleRepository.save(new Role(roleNameEnum));
            }
        });
        System.out.println("Roles seeded successfully.");
    }

    private void seedPlanTags() {
        System.out.println("Seeding PlanTags...");
        List<String> defaultPlanTagTitles = Arrays.asList(
                "Beginner", "Intermediate", "Advanced", "Strength", "Cardio",
                "Flexibility", "Weight Loss", "Muscle Gain", "Full Body",
                "Upper Body", "Lower Body", "Core"
        );
        defaultPlanTagTitles.forEach(title -> {
            if (!planTagRepository.existsByTitle(title)) {
                planTagRepository.save(new PlanTag(title));
            }
        });
        System.out.println("PlanTags seeded successfully.");
    }

    private void seedGoals() {
        System.out.println("Seeding Goals...");
        List<GoalData> goalsData = Arrays.asList(
                new GoalData("Weight Loss", new BigDecimal("0.7"), Arrays.asList("Weight Loss", "Cardio", "Beginner")),
                new GoalData("Muscle Gain", new BigDecimal("0.8"), Arrays.asList("Muscle Gain", "Strength", "Advanced")),
                new GoalData("Flexibility", new BigDecimal("0.6"), Arrays.asList("Flexibility", "Beginner")),
                new GoalData("Overall Fitness", new BigDecimal("0.75"), Arrays.asList("Full Body", "Intermediate", "Cardio", "Strength"))
        );
        goalsData.forEach(goalData -> {
            if (!goalRepository.existsByGoalName(goalData.name())) {
                List<PlanTag> associatedTags = goalData.tagTitles().stream()
                        .map(planTagRepository::findByTitle)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                Goal goal = new Goal(goalData.name(), goalData.score(), associatedTags);
                goalRepository.save(goal);
            }
        });
        System.out.println("Goals seeded successfully.");
    }

    private void seedActivityLevels() {
        System.out.println("Seeding Activity Levels...");
        List<ActivityLevelData> activityLevelsData = Arrays.asList(
                new ActivityLevelData("Sedentary", new BigDecimal("0.2"), Arrays.asList("Beginner")),
                new ActivityLevelData("Lightly Active", new BigDecimal("0.4"), Arrays.asList("Intermediate")),
                new ActivityLevelData("Moderately Active", new BigDecimal("0.6"), Arrays.asList("Advanced", "Cardio")),
                new ActivityLevelData("Very Active", new BigDecimal("0.8"), Arrays.asList("Advanced", "Strength", "Full Body"))
        );
        activityLevelsData.forEach(activityLevelData -> {
            if (!activityLevelRepository.existsByLevelName(activityLevelData.name())) {
                List<PlanTag> associatedTags = activityLevelData.tagTitles().stream()
                        .map(planTagRepository::findByTitle)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                ActivityLevel activityLevel = new ActivityLevel(activityLevelData.name(), activityLevelData.score(), associatedTags);
                activityLevelRepository.save(activityLevel);
            }
        });
        System.out.println("Activity Levels seeded successfully.");
    }

    private void seedUsers() {
        System.out.println("Seeding Users...");

        List<UserData> usersData = Arrays.asList(
                new UserData("davidalexd1234@gmail.com", "secret", Arrays.asList(Roles.ROLE_ADMIN, Roles.ROLE_USER))
        );

        usersData.forEach(userData -> {
            if (!userRepository.existsByEmail(userData.email())) {
                List<Role> userRoles = userData.roleNames().stream()
                        .map(roleRepository::findByName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                String encodedPassword = passwordEncoder.encode(userData.password());

                User user = new User(userData.email(), encodedPassword, userRoles);
                userRepository.save(user);

                // Capturar el ID del usuario admin
                if (userData.email().equals("davidalexd1234@gmail.com")) {
                    this.adminUserId = user.getId();
                }
            }
        });
        System.out.println("Users seeded successfully.");
    }

    private void seedExercises(Long userId) {
        System.out.println("Seeding Exercises for userId: " + userId + "...");

        List<ExerciseData> exercisesData = Arrays.asList(
                new ExerciseData("Push-ups", "A classic bodyweight exercise that strengthens the chest, shoulders, and triceps.", userId),
                new ExerciseData("Squats", "A fundamental lower body exercise that targets the quadriceps, hamstrings, and glutes.", userId),
                new ExerciseData("Plank", "An isometric core strength exercise that involves maintaining a position similar to a push-up.", userId),
                new ExerciseData("Burpees", "A full-body exercise combining a squat, push-up, and jump, great for cardio.", userId),
                new ExerciseData("Lunges", "A lower body exercise that works one leg at a time, improving balance and strength.", userId),
                new ExerciseData("Deadlifts", "A full-body strength exercise that involves lifting a barbell or dumbbells off the floor.", userId)
        );

        exercisesData.forEach(exerciseData -> {
            if (!exerciseRepository.existsByUserIdAndTitle(userId, exerciseData.title())) {
                Exercise exercise = new Exercise(exerciseData.title(), exerciseData.description(), exerciseData.userId());
                exerciseRepository.save(exercise);
            }
        });
        System.out.println("Exercises seeded successfully.");
    }

    private void seedWorkouts(Long userId) {
        System.out.println("Seeding Workouts for userId: " + userId + "...");

        List<WorkoutData> workoutsData = Arrays.asList(
                new WorkoutData("Full Body Blast", "A high-intensity workout targeting all major muscle groups.", userId,
                        Arrays.asList("Push-ups", "Squats", "Plank", "Burpees")),
                new WorkoutData("Lower Body Power", "Focuses on strengthening the legs and glutes.", userId,
                        Arrays.asList("Squats", "Lunges", "Deadlifts")),
                new WorkoutData("Core & Cardio", "Combines core stability with cardiovascular challenge.", userId,
                        Arrays.asList("Plank", "Burpees"))
        );

        workoutsData.forEach(workoutData -> {
            if (!workoutRepository.existsByUserIdAndTitle(userId, workoutData.title())) {
                Workout workout = new Workout(workoutData.title(), workoutData.description(), workoutData.userId());

                List<Exercise> associatedExercises = workoutData.exerciseTitles().stream()
                        .map(exerciseTitle -> exerciseRepository.findByUserIdAndTitle(userId, exerciseTitle))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                workout.setExercises(associatedExercises);

                workoutRepository.save(workout);
            }
        });
        System.out.println("Workouts seeded successfully.");
    }

    private void seedFitwisePlans(Long userId) {
        System.out.println("Seeding FitwisePlans for userId: " + userId + "...");

        List<FitwisePlanData> fitwisePlansData = Arrays.asList(
                new FitwisePlanData("Beginner Full Body Plan", "A comprehensive plan for beginners focusing on overall fitness.", userId,
                        "Full Body Blast", "Healthy Eating Basic", Arrays.asList("Beginner", "Full Body", "Overall Fitness")), // Añadir nombre de dieta
                new FitwisePlanData("Intermediate Strength Cycle", "Designed to build muscle and strength for intermediate users.", userId,
                        "Lower Body Power", "High Protein Diet", Arrays.asList("Intermediate", "Strength", "Muscle Gain")), // Añadir nombre de dieta
                new FitwisePlanData("Cardio & Core Challenge", "A plan for improving cardiovascular health and core stability.", userId,
                        "Core & Cardio", "Low Carb Diet", Arrays.asList("Cardio", "Core", "Flexibility")) // Añadir nombre de dieta
        );

        fitwisePlansData.forEach(planData -> {
            if (!fitwisePlanRepository.existsByUserIdAndTitle(userId, planData.title())) {
                FitwisePlan fitwisePlan = new FitwisePlan(planData.title(), planData.description());

                fitwisePlan.setUserId(planData.userId());

                // Asignar Workout
                Optional<Workout> workout = workoutRepository.findByUserIdAndTitle(userId, planData.workoutTitle());
                workout.ifPresent(fitwisePlan::assignWorkout);

                // Asignar Diet (Nuevo)
                Optional<Diet> diet = dietRepository.findByUserIdAndTitle(userId, planData.dietTitle());
                diet.ifPresent(fitwisePlan::assignDiet);

                // Asignar PlanTags
                List<PlanTag> associatedTags = planData.tagTitles().stream()
                        .map(planTagRepository::findByTitle)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                associatedTags.forEach(fitwisePlan::addPlanTag);

                fitwisePlanRepository.save(fitwisePlan);
            }
        });
        System.out.println("FitwisePlans seeded successfully.");
    }

    // --- Nuevos métodos de seeding para Meal y Diet ---

    private void seedMeals(Long userId) {
        System.out.println("Seeding Meals for userId: " + userId + "...");

        List<MealData> mealsData = Arrays.asList(
                new MealData("Breakfast Smoothie", "A quick and healthy breakfast smoothie with fruits and protein.", userId),
                new MealData("Chicken Salad", "A light and protein-rich lunch with grilled chicken and mixed greens.", userId),
                new MealData("Salmon with Asparagus", "A healthy dinner with baked salmon and steamed asparagus.", userId),
                new MealData("Oatmeal with Berries", "A warm and hearty breakfast with oats and fresh berries.", userId),
                new MealData("Quinoa Bowl", "A customizable lunch or dinner with quinoa, vegetables, and a protein source.", userId)
        );

        mealsData.forEach(mealData -> {
            if (!mealRepository.existsByUserIdAndTitle(userId, mealData.title())) {
                Meal meal = new Meal(mealData.title(), mealData.description(), mealData.userId());
                mealRepository.save(meal);
            }
        });
        System.out.println("Meals seeded successfully.");
    }

    private void seedDiets(Long userId) {
        System.out.println("Seeding Diets for userId: " + userId + "...");

        List<DietData> dietsData = Arrays.asList(
                new DietData("Healthy Eating Basic", "A balanced diet focusing on whole foods and portion control.", userId,
                        Arrays.asList("Breakfast Smoothie", "Chicken Salad", "Salmon with Asparagus")),
                new DietData("High Protein Diet", "Designed for muscle gain and recovery with increased protein intake.", userId,
                        Arrays.asList("Chicken Salad", "Salmon with Asparagus", "Quinoa Bowl")),
                new DietData("Low Carb Diet", "Emphasizes reduced carbohydrate intake for weight management.", userId,
                        Arrays.asList("Chicken Salad", "Salmon with Asparagus"))
        );

        dietsData.forEach(dietData -> {
            if (!dietRepository.existsByUserIdAndTitle(userId, dietData.title())) {
                Diet diet = new Diet(dietData.title(), dietData.description(), dietData.userId());

                // Obtener las comidas existentes para este usuario
                List<Meal> associatedMeals = dietData.mealTitles().stream()
                        .map(mealTitle -> mealRepository.findByUserIdAndTitle(userId, mealTitle))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                diet.setMeals(associatedMeals); // Asignar las comidas (asumiendo que Diet tiene setMeals)

                dietRepository.save(diet);
            }
        });
        System.out.println("Diets seeded successfully.");
    }


    private record GoalData(String name, BigDecimal score, List<String> tagTitles) {}
    private record ActivityLevelData(String name, BigDecimal score, List<String> tagTitles) {}
    private record UserData(String email, String password, List<Roles> roleNames) {}
    private record ExerciseData(String title, String description, Long userId) {}
    private record WorkoutData(String title, String description, Long userId, List<String> exerciseTitles) {}
    private record FitwisePlanData(String title, String description, Long userId, String workoutTitle, String dietTitle, List<String> tagTitles) {}
    private record MealData(String title, String description, Long userId) {}
    private record DietData(String title, String description, Long userId, List<String> mealTitles) {}
}