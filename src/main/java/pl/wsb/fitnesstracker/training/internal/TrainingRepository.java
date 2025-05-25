package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface TrainingRepository extends JpaRepository<Training, Long> {
    default Optional<Training> getByUserId(final Long userId) {
        return findAll()
                .stream()
                .filter(training -> Objects.equals(training.getUser().getId(), userId))
                .findFirst();
    }

    default List<Training> getFinishedTrainingsByDate(final Date afterDate) {
        return findAll()
                .stream()
                .filter(training -> training.getEndTime().after(afterDate)).toList();
    }
}
