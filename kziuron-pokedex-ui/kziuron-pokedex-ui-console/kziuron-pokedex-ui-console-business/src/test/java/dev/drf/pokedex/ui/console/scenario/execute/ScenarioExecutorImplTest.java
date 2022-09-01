package dev.drf.pokedex.ui.console.scenario.execute;

import com.google.common.collect.Lists;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.Scenario;
import dev.drf.pokedex.ui.console.command.CommandContext;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_SCENARIO_FOR_COMMAND;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScenarioExecutorImplTest {
    @Mock
    private Scenario<SearchContext, Pokemon> scenario1;
    @Mock
    private Scenario<ModifyContext, Pokemon> scenario2;

    private ScenarioExecutorImpl executor;

    @BeforeEach
    void setUp() {
        Mockito.when(scenario1.key()).thenReturn(Commands.SEARCH_BY_NAME);
        Mockito.when(scenario2.key()).thenReturn(Commands.SMART_UPDATE);

        executor = new ScenarioExecutorImpl(Lists.newArrayList(scenario1, scenario2));
    }

    @Test
    void shouldThrowException_whenScenarioNotFound() {
        // arrange
        CommandContext context = new CommandContext(Commands.AUTHORIZATION, new AuthorizationContext());

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> executor.execute(context));

        // assert
        assertEquals(NO_SCENARIO_FOR_COMMAND, error.getErrorCode());
    }

    @Test
    void shouldExecute_whenExistedSearchContext() {
        // arrange
        SearchContext searchContext = SearchContext.ofConsole();
        CommandContext context = new CommandContext(Commands.SEARCH_BY_NAME, searchContext);

        // act
        executor.execute(context);

        // assert
        ArgumentCaptor<SearchContext> argumentCaptor = ArgumentCaptor.forClass(SearchContext.class);
        Mockito.verify(scenario1).execute(argumentCaptor.capture());

        SearchContext capturedContext = argumentCaptor.getValue();
        assertSame(searchContext, capturedContext);
    }

    @Test
    void shouldExecute_whenExistedUpdateContext() {
        // arrange
        ModifyContext modifyContext = ModifyContext.ofConsole();
        CommandContext context = new CommandContext(Commands.SMART_UPDATE, modifyContext);

        // act
        executor.execute(context);

        // assert
        ArgumentCaptor<ModifyContext> argumentCaptor = ArgumentCaptor.forClass(ModifyContext.class);
        Mockito.verify(scenario2).execute(argumentCaptor.capture());

        ModifyContext capturedContext = argumentCaptor.getValue();
        assertSame(modifyContext, capturedContext);
    }
}
