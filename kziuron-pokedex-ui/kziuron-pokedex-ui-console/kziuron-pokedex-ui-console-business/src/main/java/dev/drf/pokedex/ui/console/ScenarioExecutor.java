package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.command.CommandContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;

public interface ScenarioExecutor {
    @Nonnull
    <R> ScenarioResult<R> execute(@Nonnull CommandContext context);
}
