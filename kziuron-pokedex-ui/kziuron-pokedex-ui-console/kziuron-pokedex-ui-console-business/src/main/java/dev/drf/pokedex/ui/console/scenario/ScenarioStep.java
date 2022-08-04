package dev.drf.pokedex.ui.console.scenario;

import javax.annotation.Nonnull;

/**
 * Шаг сценария
 * Нужен для выделения отдельных общих действий в единый класс
 *
 * @param <P> параметр вызова
 * @param <R> результат операции
 * @param <C> контекст операции
 */
public interface ScenarioStep<P, R, C extends ScenarioContext> {
    @Nonnull
    R process(@Nonnull P parameter,
              @Nonnull C context);
}
