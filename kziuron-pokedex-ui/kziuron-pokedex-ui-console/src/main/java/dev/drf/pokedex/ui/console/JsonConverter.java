package dev.drf.pokedex.ui.console;

import javax.annotation.Nullable;

/**
 * Универсальный интерфейс конвертера
 * @param <T> тип конвертируемого объекта
 */
public interface JsonConverter<T> {
    @Nullable
    T parse(@Nullable String json);

    @Nullable
    String toJson(@Nullable T value);
}
