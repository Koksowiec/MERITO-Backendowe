package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {
    // TODO: Dodaj komentarze
    List<Training> getAllTrainings();

    // TODO
    List<Training> getAllTrainingsByActivityType(ActivityType activityType);

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    List<Training> getTrainings(Long trainingId);

    // TODO: Dodaj komentarze
    List<Training> getAllFinishedTrainings(final Date afterTime);

    // TODO:
    Training createTraining(final Training training);
}
