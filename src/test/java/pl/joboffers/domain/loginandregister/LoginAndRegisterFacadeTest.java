package pl.joboffers.domain.loginandregister;

import org.junit.Test;
import pl.joboffers.domain.loginandregister.dto.RegisterResultDto;
import pl.joboffers.domain.loginandregister.dto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;
import pl.joboffers.domain.loginandregister.exception.UserNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginFacade = new LoginAndRegisterFacade(
            new InMemoryLoginRepository(),
            new LoginMapper()
    );

    @Test
    public void should_register_user() {
        //given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");

        //when
        RegisterResultDto registerResultDto = loginFacade.register(registerUserDto);

        //then
        assertAll(
                () -> assertThat(registerResultDto.created()).isTrue(),
                () -> assertThat(registerResultDto.username()).isEqualTo("username")
        );
    }


    @Test
    public void should_find_user_by_username() {
        //given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");
        RegisterResultDto registerResultDto = loginFacade.register(registerUserDto);

        //when
        UserDto userByName = loginFacade.findByUsername(registerResultDto.username());

        //then
        assertThat(userByName).isEqualTo(new UserDto(registerResultDto.id(), registerResultDto.username(), "password"));
    }


    @Test
    public void should_throw_exception_if_user_not_found() {
        //given
        String username = "userNotToBeFound";

        //when
        Throwable throwable = catchThrowable(() -> loginFacade.findByUsername(username));

        //then
        assertThat(throwable).isInstanceOf(UserNotFoundException.class).hasMessage("User not found!");
    }
}