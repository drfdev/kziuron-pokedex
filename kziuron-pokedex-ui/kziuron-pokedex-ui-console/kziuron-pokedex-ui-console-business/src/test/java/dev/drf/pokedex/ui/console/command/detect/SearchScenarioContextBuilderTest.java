package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.Test;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_REQUIRED_PARAMETER;
import static org.junit.jupiter.api.Assertions.*;

class SearchScenarioContextBuilderTest {
    private final SearchScenarioContextBuilder contextBuilder = new SearchScenarioContextBuilder();

    @Test
    void shouldReturnCorrectContextType() {
        assertEquals(ContextType.SEARCH, contextBuilder.contextType());
    }

    @Test
    void shouldBuildContext_whenFileParamEnabledWithPath() {
        // arrange
        String[] params = new String[]{"-file", "-path=/folder/a1.json"};

        // act
        ScenarioContext context = assertDoesNotThrow(() -> contextBuilder.build(params));

        // assert
        assertNotNull(context);
        assertEquals(SearchContext.class, context.getClass());

        SearchContext modifyContext = (SearchContext) context;
        assertEquals(DataType.FILE, modifyContext.dataType());
        assertNotNull(modifyContext.path());
        assertEquals("/folder/a1.json", modifyContext.path().toString());
    }

    @Test
    void shouldBuildContext_whenConsoleParamEnabled() {
        // arrange
        String[] params = new String[]{"-console"};

        // act
        ScenarioContext context = assertDoesNotThrow(() -> contextBuilder.build(params));

        // assert
        assertNotNull(context);
        assertEquals(SearchContext.class, context.getClass());

        SearchContext modifyContext = (SearchContext) context;
        assertEquals(DataType.CONSOLE, modifyContext.dataType());
    }

    @Test
    void shouldThrowException_whenUnknownDataType() {
        // arrange
        String[] params = new String[]{"-unknown"};

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> contextBuilder.build(params));

        // assert
        assertNotNull(error);
        assertEquals(NO_REQUIRED_PARAMETER, error.getErrorCode());
    }

    @Test
    void shouldThrowException_whenFileParamEnabledWithoutPath() {
        // arrange
        String[] params = new String[]{"-file"};

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> contextBuilder.build(params));

        // assert
        assertNotNull(error);
        assertEquals(NO_REQUIRED_PARAMETER, error.getErrorCode());
    }
}
