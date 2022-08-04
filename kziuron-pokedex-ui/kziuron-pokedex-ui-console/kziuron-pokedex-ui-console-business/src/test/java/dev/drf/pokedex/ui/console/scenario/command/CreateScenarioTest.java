package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiErrorCode;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateScenarioTest {
    @Mock
    private ConsoleService consoleService;
    @Mock
    private PokemonApiService pokemonApiService;
    @Mock
    private ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep;

    @InjectMocks
    private CreateScenario scenario;

    @Mock
    private ApiErrorCode errorCode;

    @Test
    void shouldReturnErrorResult_whenErrorApiResult() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonReadStep.process(any(), any()))
                .thenReturn(new Pokemon());
        Mockito.when(pokemonApiService.create(any()))
                .thenReturn(ApiResult.error(ApiError.of(errorCode)));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleServiceReturnNullValue() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn(null);

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INCORRECT_PARAMETERS.getCode(), result.error().code());
    }

    @Test
    void shouldReturnSuccessResult_whenSuccessApiResult() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonReadStep.process(any(), any()))
                .thenReturn(new Pokemon());
        Mockito.when(pokemonApiService.create(any()))
                .thenReturn(ApiResult.success(new Pokemon()));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleErrorThrowError() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenPokemonReadStepThrowError() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonReadStep.process(any(), any()))
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenPokemonApiServiceThrowError() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonReadStep.process(any(), any()))
                .thenReturn(new Pokemon());
        Mockito.when(pokemonApiService.create(any()))
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldCorrectKey() {
        // act - assert
        assertEquals(Commands.CREATE, scenario.key());
    }

    @Test
    void shouldReturnErrorResultWithCorrectErrorCode_whenPokemonApiServiceThrowConsoleUIError() {
        // arrange
        ModifyContext context = ModifyContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonReadStep.process(any(), any()))
                .thenReturn(new Pokemon());
        Mockito.when(pokemonApiService.create(any()))
                .thenThrow(new ConsoleUIException(NULL_PARSE_RESULT, "test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(NULL_PARSE_RESULT.getCode(), result.error().code());
    }
}
