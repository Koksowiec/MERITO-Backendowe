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

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {
        return userMapper.toDto(userService.createUser(userMapper.toEntity(userDto)));
    }

    @GetMapping("/simple")
    public List<UserSimple> getAllUsersSimple(){
        // TODO: Zad 1 - pamietaj o test driven developement, nie edytuj testow integracfyjnych,
        // Jesli testy przejda to znaczy ze zadanie jest poprawnmie wykonane
        // Javadoc ogarnij z copilotem ale pamietaj zeby to analizowac czy na pewno jest poprawne
        // Tworz na postmanie kolekcje z zaphytaniami i na koncu ja eksportujemy i dostajemy jsona,
        // Nastepnie tego jsona dodajemy do resources jako dowod

        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) throws InterruptedException {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable long id) {
        // http://localhost:2101/v1/users/1
        // Ta metoda obecnie zwraca błąd związany z kluczem obcym do encji Training.
        // Wymagania obecnie nie doprezycowały czy usunąć też kaskadowo inne encje:
        return userService.deleteUserById(id);
    }

    @GetMapping("/email")
    public List<UserSimpleEmail> getUserByEmail(@RequestParam String email) throws InterruptedException {
        // http://localhost:2101/v1/users/email?email=Emma.Johnson

        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toSimpleEmail)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserSimpleBirthdate> getUsersOlderThan(@PathVariable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate time) throws InterruptedException {
        return userService.findUsersOlderThan(time)
                .stream()
                .map(userMapper::toSimpleBirthdate)
                .toList();
    }
}