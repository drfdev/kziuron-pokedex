package dev.drf.pokedex.ui.console.command;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;

import javax.annotation.Nonnull;

public interface ScenarioContextBuilder {
    @Nonnull
    ScenarioContext build(@Nonnull String[] params);

    @Nonnull
    ContextType contextType();
}
