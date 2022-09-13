package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

public interface PokemonDeactivateService {
    @Nonnull
    Pokemon deactivate(@Nonnull Pokemon pokemon);
}
