package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.getAll();
    }

    @Override
    public List<Training> getAllTrainingsByActivityType(ActivityType activityType){
        return trainingRepository.getAllByActivityType(activityType);
    }

    @Override
    public List<Training> getTrainings(final Long trainingId) {
        return trainingRepository.getByUserId(trainingId);
    }

    @Override
    public List<Training> getAllFinishedTrainings(final Date afterTime){
        return trainingRepository.getFinishedTrainingsByDate(afterTime);
    }

    @Override
    public Training createTraining(final Training training)
    {
        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

}
