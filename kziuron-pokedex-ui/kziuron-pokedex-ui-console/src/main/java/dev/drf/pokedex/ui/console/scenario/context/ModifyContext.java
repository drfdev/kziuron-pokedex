package dev.drf.pokedex.ui.console.scenario.context;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

import javax.annotation.Nonnull;
import java.time.Instant;

/**
 * Контекст для создания, обновления и других модификаций данных
 * @param dataType тип вывода данных
 * @param operationTime врем совершения операции
 */
public record ModifyContext(@Nonnull DataType dataType,
                            @Nonnull Instant operationTime) implements ScenarioContext {
}