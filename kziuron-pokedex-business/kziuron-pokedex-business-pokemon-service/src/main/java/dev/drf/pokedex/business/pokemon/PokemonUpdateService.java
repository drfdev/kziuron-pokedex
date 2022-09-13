package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

public interface PokemonUpdateService {
    @Nonnull
    Pokemon update(@Nonnull Pokemon pokemon);
}
