package dev.drf.pokedex.ui.console;

import javax.annotation.Nonnull;

/**
 * Описание команды
 * Содержит в себе ключ команды
 */
public interface Command {
    @Nonnull
    String key();
}
