package dev.drf.pokedex.ui.console.app.stub;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import java.time.Instant;

public class PokemonApiServiceStub implements PokemonApiService {
    private final StorageService storageService;

    public PokemonApiServiceStub(StorageService storageService) {
        this.storageService = storageService;
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> get(long id) {
        return ApiResult.success(storageService.get(id)
                .orElse(null));
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> create(@Nonnull Pokemon pokemon) {
        var id = storageService.saveData(pokemon);
        return get(id);
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> deactivate(@Nonnull Pokemon pokemon) {
        pokemon.setEndDate(Instant.now());
        return update(pokemon);
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> update(@Nonnull Pokemon pokemon) {
        storageService.saveData(pokemon);
        return get(pokemon.getId());
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> smartUpdate(@Nonnull Pokemon pokemon) {
        return update(pokemon);
    }
}
