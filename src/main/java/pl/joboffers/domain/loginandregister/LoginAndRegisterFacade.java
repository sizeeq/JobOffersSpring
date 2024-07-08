package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.RegisterResultDto;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final LoginRepository repository;
    private final LoginMapper mapper;

    public UserDto findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public RegisterResultDto register(RegisterUserDto registerUserDto) {
        final User user = mapper.mapFromRegisterUserDtoToUser(registerUserDto);
        User savedUser = repository.save(user);
        return mapper.mapFromUserToRegisterResultDto(savedUser);
    }
}
