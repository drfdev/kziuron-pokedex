package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.command.CommandContext;

import javax.annotation.Nonnull;

/**
 * Класс должен распознать команду из текста пришедшего из консоли
 * и переложить данные в контекст команды
 */
public interface CommandDetector {
    @Nonnull
    CommandContext detect(@Nonnull String consoleText);
}
