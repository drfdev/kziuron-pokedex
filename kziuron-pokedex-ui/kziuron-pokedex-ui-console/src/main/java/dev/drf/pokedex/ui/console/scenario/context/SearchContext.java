package dev.drf.pokedex.ui.console.scenario.context;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

import javax.annotation.Nonnull;

/**
 * Контекст для поисковых запросов
 * @param dataType тип вывода данных
 */
public record SearchContext(@Nonnull DataType dataType) implements ScenarioContext {
}
