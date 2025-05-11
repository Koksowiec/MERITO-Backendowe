package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail().toLowerCase(), email.toLowerCase()))
                .findFirst();
    }

    default List<User> findOlderThan(@JsonFormat(pattern = "yyyy-MM-dd")LocalDate date){
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .toList();
    }
}
