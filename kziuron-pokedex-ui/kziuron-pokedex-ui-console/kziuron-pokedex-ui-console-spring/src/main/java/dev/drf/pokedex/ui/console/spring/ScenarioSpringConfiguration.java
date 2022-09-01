package dev.drf.pokedex.ui.console.spring;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.*;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.command.*;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;
import dev.drf.pokedex.ui.console.scenario.execute.ScenarioExecutorImpl;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;
import dev.drf.pokedex.ui.console.scenario.step.PokemonReadScenarioStep;
import dev.drf.pokedex.ui.console.scenario.step.PokemonWriteListScenarioStep;
import dev.drf.pokedex.ui.console.scenario.step.PokemonWriteScenarioStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

@Configuration
public class ScenarioSpringConfiguration {
    @Bean
    public Scenario<AuthorizationContext, AuthorizationToken> authorizationScenario(AuthorizationService authorizationService,
                                                                                    ConsoleService consoleService) {
        return new AuthorizationScenario(authorizationService, consoleService);
    }

    @Bean
    public Scenario<ModifyContext, Pokemon> createScenario(ConsoleService consoleService,
                                                           PokemonApiService pokemonApiService,
                                                           ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep) {
        return new CreateScenario(consoleService, pokemonApiService, pokemonReadStep);
    }

    @Bean
    public Scenario<ModifyContext, Pokemon> deactivateScenario(ConsoleService consoleService,
                                                               PokemonApiService pokemonApiService,
                                                               ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep) {
        return new DeactivateScenario(consoleService, pokemonApiService, pokemonReadStep);
    }

    @Bean
    public Scenario<SearchContext, Pokemon> getScenario(ConsoleService consoleService,
                                                        PokemonApiService pokemonApiService,
                                                        ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep) {
        return new GetScenario(consoleService, pokemonApiService, pokemonWriteStep);
    }

    @Bean
    public Scenario<SearchContext, List<Pokemon>> searchByNameScenario(ConsoleService consoleService,
                                                                       SearchPokemonApiService searchPokemonApiService,
                                                                       ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> pokemonWriteStep) {
        return new SearchByNameScenario(consoleService, searchPokemonApiService, pokemonWriteStep);
    }

    @Bean
    public Scenario<SearchContext, Pokemon> searchByVersionScenario(ConsoleService consoleService,
                                                                    SearchPokemonApiService searchPokemonApiService,
                                                                    ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep) {
        return new SearchByVersionScenario(consoleService, searchPokemonApiService, pokemonWriteStep);
    }

    @Bean
    public Scenario<ModifyContext, Pokemon> smartUpdateScenario(ConsoleService consoleService,
                                                                PokemonApiService pokemonApiService,
                                                                ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep) {
        return new SmartUpdateScenario(consoleService, pokemonApiService, pokemonReadStep);
    }

    @Bean
    public Scenario<ModifyContext, Pokemon> updateScenario(ConsoleService consoleService,
                                                           PokemonApiService pokemonApiService,
                                                           ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep) {
        return new UpdateScenario(consoleService, pokemonApiService, pokemonReadStep);
    }

    @Bean
    public ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadScenarioStep(FileService service,
                                                                              JsonConverter<Pokemon> converter) {
        return new PokemonReadScenarioStep(service, converter);
    }

    @Bean
    public ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> pokemonWriteListScenarioStep(FileService service,
                                                                                                                  ConsoleService consoleService,
                                                                                                                  JsonConverter<List<Pokemon>> jsonConverter) {
        return new PokemonWriteListScenarioStep(service, consoleService, jsonConverter);
    }

    @Bean
    public ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteScenarioStep(FileService service,
                                                                                                  ConsoleService consoleService,
                                                                                                  JsonConverter<Pokemon> jsonConverter) {
        return new PokemonWriteScenarioStep(service, consoleService, jsonConverter);
    }

    @Bean
    public ScenarioExecutor scenarioExecutor(List<Scenario<? extends ScenarioContext, ?>> scenarioList) {
        return new ScenarioExecutorImpl(scenarioList);
    }
}
