package dev.drf.pokedex.ui.console.spring;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.CommandDetector;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.command.CommandDetectorImpl;
import dev.drf.pokedex.ui.console.command.ContextTypeDetector;
import dev.drf.pokedex.ui.console.command.ScenarioContextBuilder;
import dev.drf.pokedex.ui.console.context.ConsoleServiceImpl;
import dev.drf.pokedex.ui.console.files.FileServiceImpl;
import dev.drf.pokedex.ui.console.json.PokemonJsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Scanner;

/**
 * Конфигурация требует реализации сервисов PokemonApiService и SearchPokemonApiService
 * Они должны быть получены из другого модуля через транспорт
 */
@Configuration
@Import(value = {
        ScenarioSpringConfiguration.class,
        CommandSpringConfiguration.class
})
public class UiConsoleSpringConfiguration {
    // TODO AuthorizationService не реализован

    @Bean
    public CommandDetector commandDetector(ContextTypeDetector contextTypeDetector,
                                           List<ScenarioContextBuilder> scenarioContextBuilders) {
        return new CommandDetectorImpl(contextTypeDetector, scenarioContextBuilders);
    }

    @Bean
    public ConsoleService consoleService() {
        Scanner scanner = new Scanner(System.in);
        return new ConsoleServiceImpl(scanner);
    }

    @Bean
    public FileService fileService() {
        return new FileServiceImpl();
    }

    @Bean
    public JsonConverter<Pokemon> pokemonJsonConverter() {
        return new PokemonJsonConverter();
    }

}
