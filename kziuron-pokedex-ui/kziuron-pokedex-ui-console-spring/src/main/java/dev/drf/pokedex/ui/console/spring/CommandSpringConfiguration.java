package dev.drf.pokedex.ui.console.spring;

import dev.drf.pokedex.ui.console.command.ContextTypeDetector;
import dev.drf.pokedex.ui.console.command.ScenarioContextBuilder;
import dev.drf.pokedex.ui.console.command.detect.AuthorizationScenarioContextBuilder;
import dev.drf.pokedex.ui.console.command.detect.ContextTypeDetectorImpl;
import dev.drf.pokedex.ui.console.command.detect.ModifyScenarioContextBuilder;
import dev.drf.pokedex.ui.console.command.detect.SearchScenarioContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandSpringConfiguration {
    @Bean
    public ContextTypeDetector contextTypeDetector() {
        return new ContextTypeDetectorImpl();
    }

    @Bean
    public ScenarioContextBuilder authorizationScenarioContextBuilder() {
        return new AuthorizationScenarioContextBuilder();
    }

    @Bean
    public ScenarioContextBuilder modifyScenarioContextBuilder() {
        return new ModifyScenarioContextBuilder();
    }

    @Bean
    public ScenarioContextBuilder searchScenarioContextBuilder() {
        return new SearchScenarioContextBuilder();
    }
}
