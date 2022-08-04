package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INNER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthorizationScenarioTest {
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private ConsoleService consoleService;

    @InjectMocks
    private AuthorizationScenario scenario;

    @Test
    void shouldReturnSuccessResult_whenAuthorizationServiceReturnCorrectToken() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.of(new AuthorizationToken("test-user")));
        Mockito.when(consoleService.read())
                .thenReturn("test-value");

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenAuthorizationServiceReturnNotToken() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(consoleService.read())
                .thenReturn("test-value");

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("Authorization failed", result.error().message());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleReturnNullLogin() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(consoleService.read())
                .thenAnswer(invocationOnMock -> {
                    final int count = counter.getAndIncrement();
                    if (count % 2 == 0) {
                        return null;
                    }
                    return "test-password";
                });

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("Login or password is null", result.error().message());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleReturnNullPassword() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(consoleService.read())
                .thenAnswer(invocationOnMock -> {
                    final int count = counter.getAndIncrement();
                    if (count % 2 == 0) {
                        return "test-login";
                    }
                    return null;
                });

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("Login or password is null", result.error().message());
    }

    @Test
    void shouldReturnSuccessResult_whenConsoleServiceFailedFirstAttempt() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.of(new AuthorizationToken("test-user")));
        Mockito.when(consoleService.read())
                .thenAnswer(invocationOnMock -> {
                    final int count = counter.getAndIncrement();
                    if (count >= 2) {
                        return "test-value";
                    }
                    return null;
                });

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnSuccessResult_whenConsoleServiceFailedFirstSndSecondAttempts() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.of(new AuthorizationToken("test-user")));
        Mockito.when(consoleService.read())
                .thenAnswer(invocationOnMock -> {
                    final int count = counter.getAndIncrement();
                    if (count >= 4) {
                        return "test-value";
                    }
                    return null;
                });

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldMaxThreeAttempt_whenAllTimeReturnErrorResult() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(consoleService.read())
                .thenReturn("test-value");

        // act
        scenario.execute(context);

        // assert
        Mockito.verify(authorizationService, times(3))
                .authorize(anyString(), anyString());
    }

    @Test
    void shouldCorrectMessages_whenWriteToConsoleService() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenReturn(Optional.of(new AuthorizationToken("test-user")));
        Mockito.when(consoleService.read())
                .thenReturn("test-value");

        // act
        scenario.execute(context);

        // assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(consoleService, atLeastOnce()).write(messageCaptor.capture());

        List<String> messages = messageCaptor.getAllValues();

        assertNotNull(messages);
        assertEquals(3, messages.size());

        assertEquals("Authorization, attempt 1 from 3", messages.get(0));
        assertEquals("login: ", messages.get(1));
        assertEquals("password: ", messages.get(2));
    }

    @Test
    void shouldReturnErrorResult_whenAuthorizationServiceThrowError() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(authorizationService.authorize(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("test error"));
        Mockito.when(consoleService.read())
                .thenReturn("test-value");

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleServiceThrowError() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        Mockito.when(consoleService.read())
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<AuthorizationToken> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldCorrectKey() {
        // act - assert
        assertEquals(Commands.AUTHORIZATION, scenario.key());
    }
}
