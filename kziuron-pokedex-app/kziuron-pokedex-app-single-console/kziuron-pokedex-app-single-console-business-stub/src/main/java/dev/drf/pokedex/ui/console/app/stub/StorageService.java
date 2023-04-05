package dev.drf.pokedex.ui.console.app.stub;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

/**
 * Примитивное хранилище данных
 * Добавлено для работы заглушек
 */
public interface StorageService {

    @Nonnull
    Optional<Pokemon> get(long id);

    @Nonnull
    Collection<Pokemon> getData();

    long saveData(@Nonnull Pokemon pokemon);

    @Nonnull
    Optional<Pokemon> getHistory(long id, int version);
}
