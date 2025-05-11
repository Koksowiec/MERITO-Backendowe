package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Gets all users from database.
     *
     * @return {@link List} of found {@link UserDto}
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Creates user based on request body and inserts it inside database.
     *
     * @param userDto json object of user to create
     * @return {@link UserDto} that was added
     * @throws InterruptedException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {
        return userMapper.toDto(userService.createUser(userMapper.toEntity(userDto)));
    }

    /**
     * Gets simplified version of users object from database.
     *
     * @return {@link List} of {@link UserSimple}
     */
    @GetMapping("/simple")
    public List<UserSimple> getAllUsersSimple(){
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

    /**
     * Gets user by given id.
     *
     * @param id to specify user to get
     * @return {@link UserDto} that was specified by id
     * @throws InterruptedException
     */
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) throws InterruptedException {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    /**
     * Deletes user by given id.
     *
     * @param id to specify user to delete.
     * @return 204 status or throw an exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws InterruptedException {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted");
    }

    /**
     * Gets users whose email contains request param.
     *
     * @param email to specify users to get
     * @return {@link List} of {@link UserSimpleEmail}
     * @throws InterruptedException
     */
    @GetMapping("/email")
    public List<UserSimpleEmail> getUserByEmail(@RequestParam String email) throws InterruptedException {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toSimpleEmail)
                .toList();
    }

    /**
     * Gets users that were born before given date.
     *
     * @param time to specify what users to get
     * @return {@link List} of {@link UserSimpleBirthdate}
     * @throws InterruptedException
     */
    @GetMapping("/older/{time}")
    public List<UserSimpleBirthdate> getUsersOlderThan(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate time) throws InterruptedException {
        return userService.findUsersOlderThan(time)
                .stream()
                .map(userMapper::toSimpleBirthdate)
                .toList();
    }

    /**
     * Updates user by given id and user details provided in request body.
     *
     * @param id to specify what user to update
     * @param userDto to specify update parameters
     * @return {@link UserDto}
     * @throws InterruptedException
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDto) throws InterruptedException {
        return userService.updateUser(id, userMapper.toEntity(userDto))
                .map(userMapper::toDto)
                .orElse(null);
    }
}