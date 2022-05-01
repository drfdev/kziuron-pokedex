package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.command.CommandContext;
import dev.drf.pokedex.ui.console.command.CommandResult;

import javax.annotation.Nonnull;

/**
 * Класс выполняющий команду
 * Ключ команды указывает на то, какую именно команду класс реализует
 */
public interface CommandInvoker {
    @Nonnull
    CommandResult invoke(@Nonnull CommandContext context);

    @Nonnull
    Command key();
}
