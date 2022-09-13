package dev.drf.pokedex.business.search;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface SearchByVersionService {
    @Nonnull
    Optional<Pokemon> getByVersion(long id, int version);
}
