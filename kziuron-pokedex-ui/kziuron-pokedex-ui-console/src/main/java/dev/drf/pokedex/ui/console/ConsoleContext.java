package dev.drf.pokedex.ui.console;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Контекст консоли
 * Умеет записывать и читать в/из консоли
 */
public interface ConsoleContext {
    void write(@Nonnull String text);

    @Nullable
    String read();
}
