package dev.drf.pokedex.api.core;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

@Api
public interface CoreApiService {
    @Nonnull
    ApiResult<Pokemon> create(@Nonnull Pokemon pokemon);

    @Nonnull
    ApiResult<Pokemon> update(@Nonnull Pokemon pokemon);
}
