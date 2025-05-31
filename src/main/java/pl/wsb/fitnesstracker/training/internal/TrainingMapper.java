package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.CreateTrainingRequest;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
class TrainingMapper {

    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getId(),
                new UserDto(
                        training.getUser().getId(),
                        training.getUser().getFirstName(),
                        training.getUser().getLastName(),
                        training.getUser().getBirthdate(),
                        training.getUser().getEmail()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    TrainingDto toDto(CreateTrainingRequest training) {
        return new TrainingDto(null,
                new UserDto(
                        training.userId(),
                        null,
                        null,
                        null,
                        null),
                training.startTime(),
                training.endTime(),
                training.activityType(),
                training.distance(),
                training.averageSpeed());
    }

    Training toEntity(TrainingDto trainingDto) {
        return new Training(
                new User(
                        trainingDto.user().firstName(),
                        trainingDto.user().lastName(),
                        trainingDto.user().birthdate(),
                        trainingDto.user().email()),
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed());
    }
}
