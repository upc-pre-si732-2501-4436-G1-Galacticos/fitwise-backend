package org.upc.fitwise.plan.interfaces.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.upc.fitwise.plan.domain.exceptions.*;
import org.upc.fitwise.shared.interfaces.rest.resources.MessageResource;

/**
 * Global exception handler for the 'plan' bounded context.
 * This class catches specific exceptions thrown by the application and domain layers
 * and maps them to appropriate HTTP status codes and a standardized error response body.
 */
@RestControllerAdvice
public class FitwisePlanExceptionHandler {

    // --- Excepciones de FitwisePlan ---

    @ExceptionHandler(FitwisePlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleFitwisePlanNotFoundException(FitwisePlanNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(FitwisePlanAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleFitwisePlanAlreadyExistsException(FitwisePlanAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedPlanAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResource handleUnauthorizedPlanAccessException(UnauthorizedPlanAccessException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(NoValidTagsFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleNoValidTagsFoundException(NoValidTagsFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    // --- Excepciones de Workout ---

    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleWorkoutNotFoundException(WorkoutNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(WorkoutAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleWorkoutAlreadyExistsException(WorkoutAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedWorkoutAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResource handleUnauthorizedWorkoutAccessException(UnauthorizedWorkoutAccessException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(WorkoutAlreadyAssociatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleWorkoutAlreadyAssociatedException(WorkoutAlreadyAssociatedException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(WorkoutNotAssociatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleWorkoutNotAssociatedException(WorkoutNotAssociatedException ex) {
        return new MessageResource(ex.getMessage());
    }

    // --- Excepciones de Exercise ---


    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleExerciseNotFoundException(ExerciseNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(ExerciseAlreadyExistsException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleExerciseAlreadyExistsException(ExerciseAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedExerciseAccessException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResource handleUnauthorizedExerciseAccessException(UnauthorizedExerciseAccessException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(ExerciseAlreadyAddedToWorkoutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleExerciseAlreadyAddedToWorkoutException(ExerciseAlreadyAddedToWorkoutException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(ExerciseNotAddedToWorkoutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleExerciseNotAddedToWorkoutException(ExerciseNotAddedToWorkoutException ex) {
        return new MessageResource(ex.getMessage());
    }

    // --- Excepciones de Diet ---

    @ExceptionHandler(DietNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleDietNotFoundException(DietNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(DietAlreadyExistsException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleDietAlreadyExistsException(DietAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedDietAccessException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResource handleUnauthorizedDietAccessException(UnauthorizedDietAccessException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(DietAlreadyAssociatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleDietAlreadyAssociatedException(DietAlreadyAssociatedException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(DietNotAssociatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleDietNotAssociatedException(DietNotAssociatedException ex) {
        return new MessageResource(ex.getMessage());
    }

    // --- Excepciones de Meal ---

    @ExceptionHandler(MealNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleMealNotFoundException(MealNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(MealAlreadyExistsException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleMealAlreadyExistsException(MealAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedMealAccessException.class) // Nuevo handler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResource handleUnauthorizedMealAccessException(UnauthorizedMealAccessException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(MealAlreadyAddedToDietException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleMealAlreadyAddedToDietException(MealAlreadyAddedToDietException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(MealNotAddedToDietException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleMealNotAddedToDietException(MealNotAddedToDietException ex) {
        return new MessageResource(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResource handleGenericRuntimeException(RuntimeException ex) {
        return new MessageResource("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResource handleAllOtherExceptions(Exception ex) {
        return new MessageResource("An unhandled server error occurred.");
    }

}