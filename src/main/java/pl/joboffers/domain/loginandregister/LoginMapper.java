package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.RegisterResultDto;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

class LoginMapper {

    UserDto mapToUserDto(User user) {
        return new UserDto(user.id(), user.username(), user.password());
    }

    User mapFromRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
    }

    RegisterResultDto mapFromUserToRegisterResultDto(User savedUser) {
        return new RegisterResultDto(savedUser.id(), true, savedUser.username());
    }
}
