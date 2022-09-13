package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

public interface PokemonCreateService {
    @Nonnull
    Pokemon create(@Nonnull Pokemon pokemon);
}
