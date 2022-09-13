package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

public interface PokemonSmartUpdateService {
    @Nonnull
    Pokemon smartUpdate(@Nonnull Pokemon pokemon);
}
