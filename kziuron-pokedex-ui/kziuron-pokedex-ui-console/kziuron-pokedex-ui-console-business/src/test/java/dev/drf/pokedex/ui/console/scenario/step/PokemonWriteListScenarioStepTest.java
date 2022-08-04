package dev.drf.pokedex.ui.console.scenario.step;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.error.ErrorCodes;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PokemonWriteListScenarioStepTest {
    @Mock
    private FileService service;
    @Mock
    private ConsoleService consoleService;
    @Mock
    private JsonConverter<List<Pokemon>> jsonConverter;

    @InjectMocks
    private PokemonWriteListScenarioStep scenarioStep;

    @Test
    void shouldReturnSuccessResult_whenCorrectProcessingWithFileContext() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn("json");

        // act
        ScenarioResult<List<Pokemon>> result = scenarioStep.process(new ArrayList<>(), SearchContext.ofFile(Paths.get("test")));

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldReturnSuccessResult_whenCorrectProcessingWithConsoleContext() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn("json");

        // act
        ScenarioResult<List<Pokemon>> result = scenarioStep.process(new ArrayList<>(), SearchContext.ofConsole());

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());
    }

    @Test
    void shouldThrowNullJsonException_whenJsonConverterReturnNull() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn(null);

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> scenarioStep.process(new ArrayList<>(), SearchContext.ofConsole()));

        // assert
        assertEquals(ErrorCodes.NULL_JSON_RESULT, error.getErrorCode());
    }

    @Test
    void shouldThrowNullPathException_whenFileContextWithoutPath() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn("json");
        SearchContext context = new SearchContext(DataType.FILE, null);

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> scenarioStep.process(new ArrayList<>(), context));

        // assert
        assertEquals(ErrorCodes.NULL_PATH, error.getErrorCode());
    }

    @Test
    void shouldCorrectOrder_whenScenarioStepProcessingWithConsoleContext() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn("json");

        InOrder inOrder = Mockito.inOrder(service, consoleService, jsonConverter);

        // act
        scenarioStep.process(new ArrayList<>(), SearchContext.ofConsole());

        // assert
        inOrder.verify(jsonConverter).toJson(any());
        inOrder.verify(consoleService).write(anyString());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldCorrectOrder_whenScenarioStepProcessingWithFileContext() {
        // arrange
        Mockito.when(jsonConverter.toJson(any())).thenReturn("json");

        InOrder inOrder = Mockito.inOrder(service, consoleService, jsonConverter);

        // act
        scenarioStep.process(new ArrayList<>(), SearchContext.ofFile(Paths.get("test")));

        // assert
        inOrder.verify(jsonConverter).toJson(any());
        inOrder.verify(service).writeToFile(any(), anyString());
        inOrder.verifyNoMoreInteractions();
    }
}
