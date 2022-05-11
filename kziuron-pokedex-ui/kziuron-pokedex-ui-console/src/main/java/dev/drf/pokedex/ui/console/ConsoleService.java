package dev.drf.pokedex.ui.console;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Сервис для работы с консолью
 * Умеет записывать и читать в/из консоли
 */
public interface ConsoleService {
    void write(@Nonnull String text);

    @Nullable
    String read();
}
