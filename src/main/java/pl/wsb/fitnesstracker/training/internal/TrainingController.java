package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.CreateTrainingRequest;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.UserDto;
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

    @GetMapping
    public List<TrainingDto> getTrainings(){
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getAllByActivityType(@RequestParam("activityType") String activityTypeString) throws InterruptedException{
        ActivityType activityType = ActivityType.valueOf(activityTypeString.toUpperCase());
        return trainingService.getAllTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getUserTrainings(@PathVariable long userId) throws InterruptedException {
        return trainingService.getTrainings(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> findAllFinished(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        var date = new Date(afterTime.getTime());

        return trainingService.getAllFinishedTrainings(date)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

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
}
