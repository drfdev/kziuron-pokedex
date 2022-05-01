package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.scenario.ScenarioParameters;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;

/**
 * Сценарий
 * Содержит ключ команды, для которой запускается сценарий
 * и метод запуска сценария
 */
public interface Scenario {
    @Nonnull
    ScenarioResult execute(@Nonnull ScenarioParameters parameters);

    @Nonnull
    Command key();
}
