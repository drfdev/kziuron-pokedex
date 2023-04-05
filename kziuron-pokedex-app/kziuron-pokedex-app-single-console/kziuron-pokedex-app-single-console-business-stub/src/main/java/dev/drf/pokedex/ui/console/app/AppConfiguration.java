package dev.drf.pokedex.ui.console.app;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.CommandDetector;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.ScenarioExecutor;
import dev.drf.pokedex.ui.console.app.config.ListAuthorizationConfigImpl;
import dev.drf.pokedex.ui.console.app.config.ListAuthorizationRawConfig;
import dev.drf.pokedex.ui.console.app.service.ConsoleAppService;
import dev.drf.pokedex.ui.console.app.service.ConsoleAppServiceImpl;
import dev.drf.pokedex.ui.console.app.stub.PokemonApiServiceStub;
import dev.drf.pokedex.ui.console.app.stub.SearchPokemonApiServiceStub;
import dev.drf.pokedex.ui.console.app.stub.StorageService;
import dev.drf.pokedex.ui.console.app.stub.StorageServiceStub;
import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import dev.drf.pokedex.ui.console.spring.UiConsoleSpringConfiguration;
import dev.drf.pokedex.ui.console.spring.auth.ListAuthorizationConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        ListAuthorizationConfiguration.class,
        UiConsoleSpringConfiguration.class
})
public class AppConfiguration {
    @Bean
    public ListAuthorizationConfig listAuthorizationConfig(ListAuthorizationRawConfig rawConfig) {
        return new ListAuthorizationConfigImpl(rawConfig);
    }

    @Bean
    @ConfigurationProperties("list-authorization")
    public ListAuthorizationRawConfig listAuthorizationRawConfig() {
        return new ListAuthorizationRawConfig();
    }

    // stubs:
    @Bean
    public PokemonApiService pokemonApiService(StorageService storageService) {
        return new PokemonApiServiceStub(storageService);
    }

    @Bean
    public SearchPokemonApiService searchPokemonApiService(StorageService storageService) {
        return new SearchPokemonApiServiceStub(storageService);
    }

    @Bean
    public StorageService storageService() {
        return new StorageServiceStub();
    }

    // console service:
    @Bean
    public ConsoleAppService consoleAppService(ConsoleService consoleService,
                                               CommandDetector commandDetector,
                                               AuthorizationService authorizationService,
                                               ScenarioExecutor scenarioExecutor) {
        return new ConsoleAppServiceImpl(consoleService, commandDetector, authorizationService, scenarioExecutor);
    }
}
