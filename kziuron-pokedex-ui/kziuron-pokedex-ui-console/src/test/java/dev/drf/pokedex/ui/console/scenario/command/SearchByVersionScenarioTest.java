package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.SearchPokemonApiService;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class SearchByVersionScenarioTest {
    @Mock
    private ConsoleService consoleService;
    @Mock
    private SearchPokemonApiService searchPokemonApiService;
    @Mock
    private ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep;

    @InjectMocks
    private SearchByVersionScenario scenario;

    @Mock
    private ApiErrorCode errorCode;

    @Test
    void shouldReturnErrorResult_whenErrorApiResult() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("12");
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
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
                .thenReturn("12");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.error(ScenarioError.of("test-error", "test-message")));
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
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
                .thenReturn("12");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.success(new Pokemon()));
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
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
                .thenReturn("12");
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
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
    void shouldReturnErrorResult_whenSearchPokemonApiServiceThrowError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("12");
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
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
        assertEquals(Commands.SEARCH_BY_VERSION, scenario.key());
    }

    @Test
    void shouldCorrectConsoleRead_whenExecuteWithoutError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(consoleService.read())
                .thenAnswer(invocationOnMock -> {
                    final int value = counter.getAndIncrement();
                    if (value == 0) {
                        return "123";
                    }
                    return "5";
                });
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
                .thenReturn(ApiResult.error(ApiError.of(errorCode)));

        // act
        scenario.execute(context);

        // assert
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> versionCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(searchPokemonApiService).getByVersion(idCaptor.capture(), versionCaptor.capture());

        Long id = idCaptor.getValue();
        Integer version = versionCaptor.getValue();

        assertEquals(123L, id);
        assertEquals(5, version);
    }

    @Test
    void shouldReturnErrorResultWithCorrectErrorCode_whenSearchPokemonApiServiceThrowConsoleUIError() {
        // arrange
        SearchContext context = SearchContext.ofConsole();

        Mockito.when(consoleService.read())
                .thenReturn("12");
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt()))
                .thenThrow(new ConsoleUIException(NULL_JSON_RESULT, "test error"));

        // act
        ScenarioResult<Pokemon> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(NULL_JSON_RESULT.getCode(), result.error().code());
    }
}
