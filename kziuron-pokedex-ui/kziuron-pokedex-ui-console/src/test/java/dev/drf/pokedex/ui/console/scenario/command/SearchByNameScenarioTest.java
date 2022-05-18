package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiErrorCode;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INCORRECT_PARAMETERS;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.INNER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class SearchByNameScenarioTest {
    @Mock
    private ConsoleService consoleService;
    @Mock
    private SearchPokemonApiService searchPokemonApiService;
    @Mock
    private ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> pokemonWriteStep;

    @InjectMocks
    private SearchByNameScenario scenario;

    @Mock
    private ApiErrorCode errorCode;

    @Test
    void shouldReturnErrorResult_whenErrorApiResult() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenReturn(ApiResult.error(ApiError.of(errorCode)));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenPokemonWriteStepReturnError() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.error(ScenarioError.of("test-error", "test-message")));
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenReturn(ApiResult.success(Collections.singletonList(new Pokemon())));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("test-error", result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleServiceReturnNullValue() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn(null);

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INCORRECT_PARAMETERS.getCode(), result.error().code());
    }

    @Test
    void shouldReturnSuccessResult_whenSuccessApiResult() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenReturn(ScenarioResult.success(Collections.singletonList(new Pokemon())));
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenReturn(ApiResult.success(Collections.singletonList(new Pokemon())));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnErrorResult_whenConsoleErrorThrowError() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenPokemonReadStepThrowError() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenReturn(ApiResult.success(Collections.singletonList(new Pokemon())));
        Mockito.when(pokemonWriteStep.process(any(), any()))
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenSearchPokemonApiServiceThrowError() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);

        Mockito.when(consoleService.read())
                .thenReturn("test-value");
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("test error"));

        // act
        ScenarioResult<List<Pokemon>> result = scenario.execute(context);

        // assert
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(INNER_ERROR.getCode(), result.error().code());
    }

    @Test
    void shouldCorrectKey() {
        // act - assert
        assertEquals(Commands.SEARCH_BY_NAME, scenario.key());
    }

    @Test
    void shouldCorrectConsoleRead_whenExecuteWithoutError() {
        // arrange
        SearchContext context = new SearchContext(DataType.CONSOLE);
        AtomicInteger counter = new AtomicInteger();

        Mockito.when(consoleService.read())
                        .thenAnswer(invocationOnMock -> {
                            final int value = counter.getAndIncrement();
                            if (value == 0) {
                                return "value-0";
                            }
                            if (value == 1) {
                                return "value-1";
                            }
                            return "value-2";
                        });
        Mockito.when(searchPokemonApiService.searchByPokemonName(anyString(), anyString(), anyString()))
                .thenReturn(ApiResult.error(ApiError.of(errorCode)));

        // act
        scenario.execute(context);

        // assert
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nicknameCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(searchPokemonApiService).searchByPokemonName(nameCaptor.capture(), titleCaptor.capture(), nicknameCaptor.capture());

        String name = nameCaptor.getValue();
        String title = titleCaptor.getValue();
        String nickname = nicknameCaptor.getValue();

        assertEquals("value-0", name);
        assertEquals("value-1", title);
        assertEquals("value-2", nickname);
    }
}
