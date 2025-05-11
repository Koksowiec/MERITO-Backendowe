package pl.wsb.fitnesstracker.user.api;

import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Create user.
     *
     * @param user entity to update
     * @return {@link User} created entity
     */
    User createUser(User user);

    /**
     * Delete user by provided id.
     *
     * @param id to specify what user to delete.
     */
    void deleteUserById(final Long id);

    /**
     * Updates user by id and user details.
     *
     * @param id to specify what user to update
     * @param userDto to specif user details
     * @return {@link Optional} of {@link User}
     */
    Optional<User> updateUser(final Long id, final User userDto);
}
