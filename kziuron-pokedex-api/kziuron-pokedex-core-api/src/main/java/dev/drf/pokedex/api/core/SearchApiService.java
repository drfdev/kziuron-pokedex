package dev.drf.pokedex.api.core;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.api.core.search.PokemonNameSearchParameters;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Api
public interface SearchApiService {
    @Nonnull
    ApiResult<Optional<Pokemon>> get(long id);

    @Nonnull
    ApiResult<Optional<Pokemon>> getByVersion(long id,
                                              @Nonnegative int version);

    @Nonnull
    ApiResult<List<Pokemon>> searchByPokemonName(@Nonnull PokemonNameSearchParameters searchParameters);
}
