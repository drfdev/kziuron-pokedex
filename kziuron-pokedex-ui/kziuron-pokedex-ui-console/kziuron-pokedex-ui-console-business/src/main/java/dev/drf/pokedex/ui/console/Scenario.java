package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;

/**
 * Сценарий
 * Содержит ключ команды, для которой запускается сценарий
 * и метод запуска сценария
 */
public interface Scenario<C extends ScenarioContext, R> {
    @Nonnull
    ScenarioResult<R> execute(@Nonnull C context);

    @Nonnull
    Command key();
}
