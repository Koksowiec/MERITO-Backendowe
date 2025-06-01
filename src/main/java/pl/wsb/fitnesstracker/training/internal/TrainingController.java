package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.CreateTrainingRequest;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    private final UserProvider userProvider;

    /**
     * Gets all trainings from database.
     *
     * @return {@link TrainingDto}
     * @throws InterruptedException
     */
    @GetMapping
    public List<TrainingDto> getTrainings() throws InterruptedException{
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Gets all trainings by activity type.
     *
     * @param activityTypeString to specify what trainings to get
     * @return {@link List<TrainingDto>}
     * @throws InterruptedException
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getAllByActivityType(@RequestParam("activityType") String activityTypeString) throws InterruptedException{
        ActivityType activityType = ActivityType.valueOf(activityTypeString.toUpperCase());
        return trainingService.getAllTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
    /**
     * Gets all trainings by user id.
     *
     * @param userId to specify what trainings to get
     * @return {@link List<TrainingDto>}
     * @throws InterruptedException
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getUserTrainings(@PathVariable long userId) throws InterruptedException {
        return trainingService.getTrainings(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Gets all trainings that finished, so those who are after the given time.
     *
     * @param afterTime to specify what trainings to get
     * @return {@link List<TrainingDto>}
     * @throws InterruptedException
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> findAllFinished(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        var date = new Date(afterTime.getTime());

        return trainingService.getAllFinishedTrainings(date)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Create training by CreateTrainingRequest.
     *
     * @param trainingRequest to specify the creation data
     * @return {@link TrainingDto}
     * @throws InterruptedException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrainingDto createTraining(@RequestBody CreateTrainingRequest trainingRequest) throws InterruptedException {

        var user = userProvider.getUser(trainingRequest.userId()).get();

        Training trainingDto = new Training(
                user,
                trainingRequest.startTime(),
                trainingRequest.endTime(),
                trainingRequest.activityType(),
                trainingRequest.distance(),
                trainingRequest.averageSpeed());

        return trainingMapper.toDto(trainingService.createTraining(trainingDto));
    }

    /**
     * Updates training by given id and body.
     *
     * @param trainingId to specify what training to update
     * @param trainingRequest to specify the update details
     * @return {@link List<TrainingDto>}
     * @throws InterruptedException
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable long trainingId, @RequestBody CreateTrainingRequest trainingRequest) throws InterruptedException {
        var user = userProvider.getUser(trainingRequest.userId()).get();

        Training trainingDto = new Training(
                user,
                trainingRequest.startTime(),
                trainingRequest.endTime(),
                trainingRequest.activityType(),
                trainingRequest.distance(),
                trainingRequest.averageSpeed());

        trainingDto.setId(trainingId);

        return trainingMapper.toDto(trainingService.updateTraining(trainingDto).orElse(null));
    }
}
