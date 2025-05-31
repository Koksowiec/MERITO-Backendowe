package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.util.Date;

public record TrainingDto(@Nullable Long id,
                          UserDto user,
                          Date startTime,
                          Date endTime,
                          ActivityType activityType,
                          double distance,
                          double averageSpeed) {
}
