package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    UserSimple toSimple(User user) {
        return new UserSimple(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    UserSimpleEmail toSimpleEmail(User user) {
        return new UserSimpleEmail(user.getId(),
                user.getEmail());
    }

    UserSimpleBirthdate toSimpleBirthdate(User user) {
        return new UserSimpleBirthdate(user.getFirstName(),
                user.getLastName(),
                user.getBirthdate());
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

}
