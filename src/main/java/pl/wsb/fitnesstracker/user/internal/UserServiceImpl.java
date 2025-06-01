package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * All the methods inside are described within inherited classes.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        var test = userRepository.findByEmail(email);
        return test;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(final Long id){
        if (userRepository.findById(id).orElse(null) == null)
        {
            throw new IllegalArgumentException("User with given id " + id + " does not exist!");
        }

        userRepository.deleteById(id);
    }

    @Override
    public List<User> findUsersOlderThan(@JsonFormat(pattern = "yyyy-MM-dd") final LocalDate olderThan) {
        return userRepository.findOlderThan(olderThan);
    }

    @Override
    public Optional<User> updateUser(final Long id, final User userToUpdate){
        User user = userRepository.findById(id).orElse(null);

        user.setId(id);
        user.setFirstName(userToUpdate.getFirstName());
        user.setLastName(userToUpdate.getLastName());
        user.setEmail(userToUpdate.getEmail());
        user.setBirthdate(userToUpdate.getBirthdate());

        return Optional.of(userRepository.save(user));
    }
}