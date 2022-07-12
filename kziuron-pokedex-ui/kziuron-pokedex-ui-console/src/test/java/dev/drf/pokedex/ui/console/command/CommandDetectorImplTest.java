package dev.drf.pokedex.ui.console.command;

import com.google.common.collect.Lists;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_COMMAND;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_CONTEXT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CommandDetectorImplTest {
    @Mock
    private ContextTypeDetector contextTypeDetector;
    @Mock
    private ScenarioContextBuilder contextBuilder;

    private CommandDetectorImpl commandDetector;

    @BeforeEach
    public void setUp() {
        Mockito.when(contextBuilder.contextType()).thenReturn(ContextType.SEARCH);
        commandDetector = new CommandDetectorImpl(contextTypeDetector, Lists.newArrayList(contextBuilder));
    }

    @Test
    void shouldDetectKnownCommand_whenCommandWithCorrectContextType() {
        // arrange
        String text = "get id=100";

        Mockito.when(contextTypeDetector.detect(any())).thenReturn(ContextType.SEARCH);
        Mockito.when(contextBuilder.build(any())).thenReturn(SearchContext.ofConsole());

        // act
        CommandContext commandContext = commandDetector.detect(text);

        // assert
        assertNotNull(commandContext);

        assertEquals(Commands.GET, commandContext.command());
        assertNotNull(commandContext.parameters());

        ScenarioContext scenarioContext = commandContext.parameters();
        assertEquals(SearchContext.class, scenarioContext.getClass());
    }

    @Test
    void shouldDetectKnownCommand_whenConsoleTextContainManyWhiteCharacters() {
        // arrange
        String text = "   get     id=100   ";

        Mockito.when(contextTypeDetector.detect(any())).thenReturn(ContextType.SEARCH);
        Mockito.when(contextBuilder.build(any())).thenReturn(SearchContext.ofConsole());

        // act
        CommandContext commandContext = commandDetector.detect(text);

        // assert
        assertNotNull(commandContext);

        assertEquals(Commands.GET, commandContext.command());
        assertNotNull(commandContext.parameters());

        ScenarioContext scenarioContext = commandContext.parameters();
        assertEquals(SearchContext.class, scenarioContext.getClass());
    }

    @Test
    void shouldThrowException_whenContextBuilderNotFound() {
        // arrange
        String text = "search-name name=abc";

        // act - assert
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> commandDetector.detect(text));

        // assert
        assertEquals(UNKNOWN_CONTEXT_TYPE, error.getErrorCode());
    }

    @Test
    void shouldThrowException_whenUnknownCommand() {
        // arrange
        String text = "unknown name=abc";

        // act - assert
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> commandDetector.detect(text));

        // assert
        assertEquals(UNKNOWN_COMMAND, error.getErrorCode());
    }

    @Test
    void shouldCorrectCallBuilder_whenCommandHasParams() {
        // arrange
        String text = "get -id=100 -file";

        Mockito.when(contextTypeDetector.detect(any())).thenReturn(ContextType.SEARCH);
        Mockito.when(contextBuilder.build(any())).thenReturn(SearchContext.ofConsole());

        // act
        commandDetector.detect(text);

        // assert
        ArgumentCaptor<String[]> paramArray = ArgumentCaptor.forClass(String[].class);
        Mockito.verify(contextBuilder).build(paramArray.capture());

        String[] values = paramArray.getValue();
        assertNotNull(values);
        assertEquals(2, values.length);

        assertEquals("-id=100", values[0]);
        assertEquals("-file", values[1]);
    }
}
