package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.RegisterResultDto;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

class LoginMapper {

    UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .build();
    }

    User mapFromRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
    }

    RegisterResultDto mapFromUserToRegisterResultDto(User savedUser) {
        return RegisterResultDto.builder()
                .id(savedUser.id())
                .created(true)
                .username(savedUser.username())
                .build();
    }
}
