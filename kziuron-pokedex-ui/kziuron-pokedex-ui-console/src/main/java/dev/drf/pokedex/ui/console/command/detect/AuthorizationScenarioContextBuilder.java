package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.command.ScenarioContextBuilder;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;

import javax.annotation.Nonnull;

public class AuthorizationScenarioContextBuilder implements ScenarioContextBuilder {
    @Nonnull
    @Override
    public ScenarioContext build(@Nonnull String[] params) {
        return new AuthorizationContext();
    }

    @Nonnull
    @Override
    public ContextType contextType() {
        return ContextType.AUTHORIZATION;
    }
}
