package org.upc.fitwise.plan.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.exceptions.ExerciseAlreadyExistsException;
import org.upc.fitwise.plan.domain.exceptions.ExerciseNotFoundException;
import org.upc.fitwise.plan.domain.exceptions.UnauthorizedExerciseAccessException;
import org.upc.fitwise.plan.domain.model.aggregates.Exercise;
import org.upc.fitwise.plan.domain.model.commands.CreateExerciseCommand;
import org.upc.fitwise.plan.domain.model.commands.UpdateExerciseCommand;
import org.upc.fitwise.plan.domain.services.ExerciseCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.ExerciseRepository;

import java.util.Optional;

@Service
public class ExerciseCommandServiceImpl implements ExerciseCommandService {
    private final ExerciseRepository exerciseRepository;
    private final ExternalIamService externalIamService;
    public ExerciseCommandServiceImpl(ExerciseRepository exerciseRepository,ExternalIamService externalIamService) {
        this.exerciseRepository= exerciseRepository;
        this.externalIamService= externalIamService;
    }

    @Override
    public Long handle(CreateExerciseCommand command) {

        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedExerciseAccessException("Unable to create exercise because user does not exist or is unauthorized."));

        if (exerciseRepository.existsByTitle(command.title())) {
            throw new ExerciseAlreadyExistsException(command.title());
        }

        var exercise = new Exercise(command.title(), command.description(), userId);

        exerciseRepository.save(exercise);
        return exercise.getId();
    }


    @Override
    public Optional<Exercise> handle(UpdateExerciseCommand command) {
        Long userId = externalIamService.fetchUserIdByEmail(command.username())
                .orElseThrow(() -> new UnauthorizedExerciseAccessException("Unable to update exercise because user does not exist or is unauthorized."));

        if (exerciseRepository.existsByTitleAndIdIsNot(command.title(), command.exerciseId())) {
            throw new ExerciseAlreadyExistsException(command.title());
        }

        var result = exerciseRepository.findById(command.exerciseId());
        if (result.isEmpty()) {
            throw new ExerciseNotFoundException(command.exerciseId());
        }

        var exerciseToUpdate = result.get();
        if (!userId.equals(exerciseToUpdate.getUserId())) {
            throw new UnauthorizedExerciseAccessException("Update not permitted: exercise belongs to a different user");
        }

        var updatedExercise = exerciseRepository.save(exerciseToUpdate.updateInformation(command.title(), command.description()));
        return Optional.of(updatedExercise);
    }


}
