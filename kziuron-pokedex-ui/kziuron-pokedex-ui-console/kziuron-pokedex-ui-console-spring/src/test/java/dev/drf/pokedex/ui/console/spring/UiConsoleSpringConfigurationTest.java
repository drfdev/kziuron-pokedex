package dev.drf.pokedex.ui.console.spring;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.model.dictionary.ElementType;
import dev.drf.pokedex.model.dictionary.PokemonType;
import dev.drf.pokedex.ui.console.*;
import dev.drf.pokedex.ui.console.command.CommandContext;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.command.GetScenario;
import dev.drf.pokedex.ui.console.scenario.command.UpdateScenario;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import dev.drf.pokedex.ui.console.spring.stubconfig.UiConsoleStubConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UiConsoleSpringConfiguration.class,
        UiConsoleStubConfig.class
})
class UiConsoleSpringConfigurationTest {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PokemonApiService pokemonApiService;

    @Autowired
    private SearchPokemonApiService searchPokemonApiService;

    @Autowired
    private CommandDetector commandDetector;

    @Autowired
    private List<Scenario<? extends ScenarioContext, ?>> scenarioList;

    @Autowired
    private ScenarioExecutor scenarioExecutor;

    @Autowired
    private ConsoleService consoleService;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(pokemonApiService, searchPokemonApiService, consoleService);
    }

    @Test
    void shouldDetectCommandGet_whenCorrectGetCommand() {
        // arrange
        String text = "get -console";

        Mockito.when(consoleService.read()).thenReturn("12500");
        Mockito.when(pokemonApiService.get(anyLong())).thenReturn(ApiResult.success(new Pokemon()));

        // act
        CommandContext context = commandDetector.detect(text);

        // assert
        assertNotNull(context);
        assertEquals(Commands.GET, context.command());
        assertNotNull(context.parameters());

        ScenarioContext parameters = context.parameters();
        assertEquals(SearchContext.class, parameters.getClass());

        SearchContext searchContext = (SearchContext) parameters;
        assertEquals(ContextType.SEARCH, searchContext.contextType());
        assertEquals(DataType.CONSOLE, searchContext.dataType());

        Scenario<? extends ScenarioContext, ?> scenario = null;
        for (Scenario<? extends ScenarioContext, ?> item : scenarioList) {
            if (item.key() == context.command()) {
                scenario = item;
            }
        }

        // assert search scenario
        assertNotNull(scenario);
        assertEquals(GetScenario.class, scenario.getClass());
        GetScenario getScenario = (GetScenario) scenario;

        // act execute scenario
        ScenarioResult<Pokemon> result = getScenario.execute(searchContext);

        // assert execute scenario
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());
        assertNotNull(result.payload());

        ArgumentCaptor<Long> apiCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(pokemonApiService).get(apiCaptor.capture());

        Long id = apiCaptor.getValue();
        assertEquals(12_500L, id);
    }

    @Test
    void shouldExecuteCommand_whenCorrectUpdateCommandExecuted() throws IOException {
        // arrange
        String text = "update -console";

        File tempFile = File.createTempFile("pokemon-to-update", ".json");
        tempFile.deleteOnExit();

        String data = """
                {
                    "id":10000,
                    "pokemonType":{
                        "code":2,
                        "name":"test-type"
                    },
                    "pokemonElement":{
                        "code":3,
                        "name":"test-el"
                    }
                }
                """;
        Path filePath = Paths.get(tempFile.getAbsolutePath());
        Files.writeString(filePath, data);

        Mockito.when(consoleService.read()).thenReturn(tempFile.getAbsolutePath());
        Mockito.when(pokemonApiService.update(any())).thenAnswer(invocationOnMock -> {
            Pokemon pokemon = invocationOnMock.getArgument(0, Pokemon.class);
            return ApiResult.success(pokemon);
        });

        // act - detect
        CommandContext context = commandDetector.detect(text);

        // assert - detect
        assertNotNull(context);
        assertEquals(Commands.UPDATE, context.command());
        assertNotNull(context.parameters());

        ScenarioContext parameters = context.parameters();
        assertEquals(ModifyContext.class, parameters.getClass());

        // act search scenario
        Scenario<? extends ScenarioContext, ?> scenario = null;
        for (Scenario<? extends ScenarioContext, ?> item : scenarioList) {
            if (item.key() == context.command()) {
                scenario = item;
            }
        }

        // assert search scenario
        assertNotNull(scenario);
        assertEquals(UpdateScenario.class, scenario.getClass());
        UpdateScenario updateScenario = (UpdateScenario) scenario;

        // act execute scenario
        ScenarioResult<Pokemon> result = updateScenario.execute((ModifyContext) context.parameters());

        // assert execute scenario
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());

        Pokemon pokemon = result.payload();
        assertNotNull(pokemon);
        assertEquals(10_000L, pokemon.getId());
        assertNotNull(pokemon.getPokemonType());
        assertNotNull(pokemon.getPokemonElement());

        PokemonType pokemonType = pokemon.getPokemonType();
        assertEquals(2L, pokemonType.getCode());
        assertEquals("test-type", pokemonType.getName());

        ElementType elementType = pokemon.getPokemonElement();
        assertEquals(3L, elementType.getCode());
        assertEquals("test-el", elementType.getName());

        ArgumentCaptor<Pokemon> updateCaptor = ArgumentCaptor.forClass(Pokemon.class);
        Mockito.verify(pokemonApiService).update(updateCaptor.capture());

        Pokemon target = updateCaptor.getValue();

        assertNotNull(target);
        assertEquals(10_000L, target.getId());
        assertNotNull(target.getPokemonType());
        assertNotNull(target.getPokemonElement());

        PokemonType targetPokemonType = target.getPokemonType();
        assertEquals(2L, targetPokemonType.getCode());
        assertEquals("test-type", targetPokemonType.getName());

        ElementType targetElementType = target.getPokemonElement();
        assertEquals(3L, targetElementType.getCode());
        assertEquals("test-el", targetElementType.getName());
    }

    @Test
    void shouldDetectCommandSearch_whenCorrectSearchCommandAndUseScenarioExecuter() {
        // arrange
        String text = "search-version -console";

        AtomicInteger counter = new AtomicInteger(0);
        Mockito.when(consoleService.read()).then(invocationOnMock -> {
            int value = counter.incrementAndGet();
            return value == 1 ? "12500" : "4";
        });
        Mockito.when(searchPokemonApiService.getByVersion(anyLong(), anyInt())).thenReturn(ApiResult.success(new Pokemon()));

        // act
        CommandContext context = commandDetector.detect(text);

        // assert
        assertNotNull(context);
        assertEquals(Commands.SEARCH_BY_VERSION, context.command());
        assertNotNull(context.parameters());

        ScenarioContext parameters = context.parameters();
        assertEquals(SearchContext.class, parameters.getClass());

        SearchContext searchContext = (SearchContext) parameters;
        assertEquals(ContextType.SEARCH, searchContext.contextType());
        assertEquals(DataType.CONSOLE, searchContext.dataType());

        // execute with executor
        ScenarioResult<Pokemon> result = scenarioExecutor.execute(context);

        // assert execute scenario
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());
        assertNotNull(result.payload());

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> versionCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(searchPokemonApiService).getByVersion(idCaptor.capture(), versionCaptor.capture());

        Long id = idCaptor.getValue();
        Integer version = versionCaptor.getValue();

        assertEquals(12_500L, id);
        assertEquals(4, version);
    }
}
