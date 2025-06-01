package pl.wsb.fitnesstracker.notification.internal;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.mail.internal.EmailServiceImpl;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserServiceImpl;

import java.util.List;

@Component
public class NotificationServiceImpl {

    private final EmailServiceImpl emailService;
    private final UserServiceImpl userService;

    public NotificationServiceImpl(EmailServiceImpl emailService, UserServiceImpl userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Scheduled(cron = "@monthly")
    public String sendTrainingNotification() {
        try{
            var users = userService.findAllUsers();
            for (var user : users) {
                var trainings = user.getTrainings();

                StringBuilder message = new StringBuilder();
                for (var training : trainings) {
                    String trainingRaport = """
                        Training report:
                        Start time: %s,
                        End time: %s,
                        Activity type: %s,
                        Distance: %s,
                        Average speed: %s
                        """.formatted(training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());
                    message.append(trainingRaport);
                }

                System.out.println("Sending email to: " + user.getEmail());
                emailService.sendSimpleMessage(user.getEmail(), "Monthly training report", message.toString());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Something went wrong during sending emails!";
        }

        return "Emails have been sent!";
    }
}
