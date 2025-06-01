package pl.wsb.fitnesstracker.mail.internal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import pl.wsb.fitnesstracker.mail.api.EmailSender;

/**
 * Configuration of the {@link EmailSender} (additional to the Spring mail configuration for {@link JavaMailSender} bean autoconfiguration).
 */
@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
class MailProperties {

    /**
     * Host that email uses.
     */
    private String host;

    /**
     * Port that email uses.
     */
    private int port;

    /**
     * Username to log in to the email.
     */
    private String username;

    /**
     * Password to log in to the email.
     */
    private String password;
}
