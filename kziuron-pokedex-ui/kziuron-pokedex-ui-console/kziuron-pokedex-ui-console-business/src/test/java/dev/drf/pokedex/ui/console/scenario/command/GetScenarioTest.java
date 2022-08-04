package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiErrorCode;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class GetScenarioTest {
    @Mock
    private ConsoleService consoleService;
    @Mock
    private PokemonApiService pokemonApiService;
    @Mock
    private ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep;

    @InjectMocks
    private GetScenario scenario;

    @Mock
    private ApiErrorCode errorCode;

    @Test
    void shouldReturnErrorResult_whenErrorApiResult() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenReturn(ApiResult.error(ApiError.of(errorCode)));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenPokemonWriteStepReturnError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.error(ScenarioError.of("test-error", "test-message")));
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenReturn(ApiResult.success(new Pokemon()));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("test-error", result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleServiceReturnNullValue() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

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
    void shouldReturnErrorResult_whenConsoleServiceReturnNotNumericValue() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("not-numeric");

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnSuccessResult_whenSuccessApiResult() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.success(new Pokemon()));
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenReturn(ApiResult.success(new Pokemon()));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleErrorThrowError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

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
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenReturn(ApiResult.success(new Pokemon()));
        Mockito.when(pokemonWriteStep.process(any(), any()))
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
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonApiService.get(anyLong()))
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
        assertEquals(Commands.GET, scenario.key());
    }

    @Test
    void shouldReturnErrorResultWithCorrectErrorCode_whenPokemonReadStepThrowConsoleUIError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenReturn(ApiResult.success(new Pokemon()));
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenThrow(new ConsoleUIException(AUTHORIZATION_FAILED, "test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(AUTHORIZATION_FAILED.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResultWithCorrectErrorCode_whenPokemonApiServiceThrowConsoleUIError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("123");
        Mockito.when(pokemonApiService.get(anyLong()))
                .thenThrow(new ConsoleUIException(NULL_API_RESULT, "test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(NULL_API_RESULT.getCode(), result.error().code());
    }
}
