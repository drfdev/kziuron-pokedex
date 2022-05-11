package dev.drf.pokedex.ui.console.command;

import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

import javax.annotation.Nonnull;

/**
 * Контекст команды
 *
 * @param command    - тип команды
 * @param parameters - параметры запуска
 */
public record CommandContext(@Nonnull Command command,
                             @Nonnull ScenarioContext parameters) {
}
