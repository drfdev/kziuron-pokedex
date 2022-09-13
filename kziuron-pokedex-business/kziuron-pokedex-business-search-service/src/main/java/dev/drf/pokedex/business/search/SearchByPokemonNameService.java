package dev.drf.pokedex.business.search;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface SearchByPokemonNameService {
    @Nonnull
    List<Pokemon> searchByPokemonName(@Nullable String name,
                                      @Nullable String title,
                                      @Nullable String nickname);
}
