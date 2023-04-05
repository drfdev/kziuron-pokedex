package dev.drf.pokedex.ui.console.app.stub;

import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.model.PokemonName;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPokemonApiServiceStub implements SearchPokemonApiService {
    private final StorageService storageService;

    public SearchPokemonApiServiceStub(StorageService storageService) {
        this.storageService = storageService;
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> getByVersion(long id, int version) {
        return ApiResult.success(storageService.getHistory(id, version)
                .orElse(null));
    }

    @Nonnull
    @Override
    public ApiResult<List<Pokemon>> searchByPokemonName(@Nullable String name,
                                                        @Nullable String title,
                                                        @Nullable String nickname) {
        var data = storageService.getData();
        return ApiResult.success(
                data.stream()
                        .filter(it -> it.getPokemonName() != null)
                        .filter(it -> isCorrectName(it.getPokemonName(), name, title, nickname))
                        .collect(Collectors.toList())
        );
    }

    private boolean isCorrectName(@Nonnull PokemonName pokemonName,
                                  @Nullable String name,
                                  @Nullable String title,
                                  @Nullable String nickname) {
        return StringUtils.equalsIgnoreCase(pokemonName.getName(), name)
                || StringUtils.equalsIgnoreCase(pokemonName.getTitle(), title)
                || StringUtils.equalsIgnoreCase(pokemonName.getNickname(), nickname);
    }
}
