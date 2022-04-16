package dev.drf.pokedex.api.core.search;

import javax.annotation.Nullable;

public record PokemonNameSearchParameters(@Nullable String name,
                                          @Nullable String title,
                                          @Nullable String nickname) {
}
