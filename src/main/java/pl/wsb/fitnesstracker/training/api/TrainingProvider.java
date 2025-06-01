package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {
    /**
     * Retrieves all trainings.
     *
     * @return An {@link List<Training>} containing the located Trainings
     */
    List<Training> getAllTrainings();

    /**
     * Retrieves a training based on their activity types.
     *
     * @param activityType of the trainings to be searched
     * @return An {@link List<Training>} containing the located Trainings
     */
    List<Training> getAllTrainingsByActivityType(ActivityType activityType);

    /**
     * Retrieves a training based on user ID.
     *
     * @param trainingId of the trainings to be searched
     * @return An {@link List<Training>} containing the located Training
     */
    List<Training> getTrainings(Long trainingId);

    /**
     * Retrieves a training based on their end time.
     *
     * @param afterTime of the trainings to be searched
     * @return An {@link List<Training>} containing the located Training
     */
    List<Training> getAllFinishedTrainings(final Date afterTime);

    /**
     * Creates training based on the parameter.
     *
     * @param training of what to create
     * @return An {@link Training} containing the located Training
     */
    Training createTraining(final Training training);

    /**
     * Updates training based on the parameter
     *
     * @param training of what to update
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> updateTraining(final Training training);
}
