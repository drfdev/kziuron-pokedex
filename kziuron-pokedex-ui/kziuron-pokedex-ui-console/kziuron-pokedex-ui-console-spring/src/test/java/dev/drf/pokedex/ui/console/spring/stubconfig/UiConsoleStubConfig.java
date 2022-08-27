package dev.drf.pokedex.ui.console.spring.stubconfig;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.ConsoleService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UiConsoleStubConfig {
    @Bean
    public AuthorizationService authorizationService() {
        return Mockito.mock(AuthorizationService.class);
    }

    @Bean
    public PokemonApiService pokemonApiService() {
        return Mockito.mock(PokemonApiService.class);
    }

    @Bean
    public SearchPokemonApiService searchPokemonApiService() {
        return Mockito.mock(SearchPokemonApiService.class);
    }

    @Bean
    @Primary
    public ConsoleService consoleService() {
        return Mockito.mock(ConsoleService.class);
    }
}
