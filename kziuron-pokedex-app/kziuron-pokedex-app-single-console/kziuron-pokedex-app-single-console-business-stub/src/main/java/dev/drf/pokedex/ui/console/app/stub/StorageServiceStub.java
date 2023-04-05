package dev.drf.pokedex.ui.console.app.stub;

import dev.drf.pokedex.model.Pokemon;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class StorageServiceStub implements StorageService {
    private final Map<Long, Pokemon> data = new HashMap<>();
    private final Map<ImmutablePair<Long, Integer>, Pokemon> history = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1L);

    @Nonnull
    @Override
    public Optional<Pokemon> get(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Nonnull
    @Override
    public Collection<Pokemon> getData() {
        return data.values();
    }

    @Override
    public long saveData(@Nonnull Pokemon pokemon) {
        if (pokemon.getId() == null) {
            pokemon.setId(idGenerator.getAndIncrement());
            pokemon.setVersion(1);
        } else {
            var version = pokemon.getVersion();
            pokemon.setVersion(version + 1);
        }
        data.put(pokemon.getId(), pokemon);
        history.put(ImmutablePair.of(pokemon.getId(), pokemon.getVersion()), pokemon);
        return pokemon.getId();
    }

    @Nonnull
    @Override
    public Optional<Pokemon> getHistory(long id, int version) {
        return Optional.ofNullable(history.get(ImmutablePair.of(id, version)));
    }
}
