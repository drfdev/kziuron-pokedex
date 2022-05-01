package dev.drf.pokedex.ui.console.context;

import dev.drf.pokedex.ui.console.ConsoleContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Реализация контекста консоли
 * TODO: пока не реализовано
 */
public class ConsoleContextImpl implements ConsoleContext {
    @Override
    public void write(@Nonnull String text) {
        // TODO not implemented
    }

    @Nullable
    @Override
    public String read() {
        // TODO not implemented
        return null;
    }
}
