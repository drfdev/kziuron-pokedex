package dev.drf.pokedex.ui.console.scenario.context;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * Контекст для поисковых запросов
 *
 * @param dataType тип вывода данных
 */
public record SearchContext(@Nonnull DataType dataType,
                            @Nullable Path path) implements ScenarioContext {
    @Nonnull
    public static SearchContext ofConsole() {
        return new SearchContext(DataType.CONSOLE, null);
    }

    @Nonnull
    public static SearchContext ofFile(@Nonnull Path path) {
        requireNonNull(path);
        return new SearchContext(DataType.FILE, path);
    }

    @Override
    public ContextType contextType() {
        return ContextType.SEARCH;
    }
}
