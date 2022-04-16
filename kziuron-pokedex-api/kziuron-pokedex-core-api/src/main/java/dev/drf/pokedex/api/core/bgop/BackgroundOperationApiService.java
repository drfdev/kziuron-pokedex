package dev.drf.pokedex.api.core.bgop;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.api.common.async.AsyncResult;

import javax.annotation.Nonnull;

@Api
public interface BackgroundOperationApiService {
    @Nonnull
    AsyncResult<ApiResult<BackgroundOperation>> create(@Nonnull OperationType operationType,
                                                       long pokemonId,
                                                       int pokemonVersion);
}
