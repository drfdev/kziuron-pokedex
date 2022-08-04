package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import org.junit.jupiter.api.Test;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_CONTEXT_TYPE;
import static org.junit.jupiter.api.Assertions.*;

class ContextTypeDetectorImplTest {
    private final ContextTypeDetectorImpl typeDetector = new ContextTypeDetectorImpl();

    @Test
    void shouldCorrectDetectType_byAnyCommands() {
        for (Commands command : Commands.values()) {
            // act - assert
            assertDoesNotThrow(() -> typeDetector.detect(command));
        }
    }

    @Test
    void shouldThrowException_whenUnknownCommandDetection() {
        // arrange
        Command command = () -> "UNKNOWN";

        // act - assert
        ConsoleUIException err = assertThrows(ConsoleUIException.class, () -> typeDetector.detect(command));

        // assert
        assertEquals(UNKNOWN_CONTEXT_TYPE, err.getErrorCode());
    }

    @Test
    void shouldDetectAuthorizationType_whenAuthorizationCommand() {
        // act - assert
        assertEquals(ContextType.AUTHORIZATION, typeDetector.detect(Commands.AUTHORIZATION));
    }

    @Test
    void shouldDetectSearchType_whenAllSearchCommands() {
        // act - assert
        assertEquals(ContextType.SEARCH, typeDetector.detect(Commands.GET));
        assertEquals(ContextType.SEARCH, typeDetector.detect(Commands.SEARCH_BY_NAME));
        assertEquals(ContextType.SEARCH, typeDetector.detect(Commands.SEARCH_BY_VERSION));
    }

    @Test
    void shouldDetectModifyType_whenAllModifyCommands() {
        // act - assert
        assertEquals(ContextType.MODIFY, typeDetector.detect(Commands.CREATE));
        assertEquals(ContextType.MODIFY, typeDetector.detect(Commands.UPDATE));
        assertEquals(ContextType.MODIFY, typeDetector.detect(Commands.DEACTIVATE));
        assertEquals(ContextType.MODIFY, typeDetector.detect(Commands.SMART_UPDATE));
    }
}
