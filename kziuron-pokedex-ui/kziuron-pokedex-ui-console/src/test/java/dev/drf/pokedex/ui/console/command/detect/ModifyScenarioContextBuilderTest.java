package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import org.junit.jupiter.api.Test;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_REQUIRED_PARAMETER;
import static org.junit.jupiter.api.Assertions.*;

class ModifyScenarioContextBuilderTest {
    private final ModifyScenarioContextBuilder contextBuilder = new ModifyScenarioContextBuilder();

    @Test
    void shouldReturnCorrectContextType() {
        assertEquals(ContextType.MODIFY, contextBuilder.contextType());
    }

    @Test
    void shouldBuildContext_whenFileParamEnabled() {
        // arrange
        String[] params = new String[]{"-file"};

        // act
        ScenarioContext context = assertDoesNotThrow(() -> contextBuilder.build(params));

        // assert
        assertNotNull(context);
        assertEquals(ModifyContext.class, context.getClass());

        ModifyContext modifyContext = (ModifyContext) context;
        assertEquals(DataType.FILE, modifyContext.dataType());
    }

    @Test
    void shouldBuildContext_whenConsoleParamEnabled() {
        // arrange
        String[] params = new String[]{"-console"};

        // act
        ScenarioContext context = assertDoesNotThrow(() -> contextBuilder.build(params));

        // assert
        assertNotNull(context);
        assertEquals(ModifyContext.class, context.getClass());

        ModifyContext modifyContext = (ModifyContext) context;
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
}
