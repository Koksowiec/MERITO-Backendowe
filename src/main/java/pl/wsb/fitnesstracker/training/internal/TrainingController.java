package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.mail.internal.EmailServiceImpl;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    private final EmailServiceImpl emailService;

    @GetMapping("/{userId}")
    public TrainingDto getUserTrainings(@PathVariable long userId) throws InterruptedException {
        emailService.sendSimpleMessage("asia.kolesinska@gmail.com", "Test", "Test");

        return trainingService.getTraining(userId)
                .map(trainingMapper::toDto)
                .orElse(null);
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> findAllFinished(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        var date = new Date(afterTime.getTime());

        return trainingService.getAllFinishedTrainings(date)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}
