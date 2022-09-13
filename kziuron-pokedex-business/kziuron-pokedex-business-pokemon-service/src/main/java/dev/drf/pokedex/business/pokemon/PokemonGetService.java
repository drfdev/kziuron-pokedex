package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface PokemonGetService {
    @Nonnull
    Optional<Pokemon> get(long id);
}
