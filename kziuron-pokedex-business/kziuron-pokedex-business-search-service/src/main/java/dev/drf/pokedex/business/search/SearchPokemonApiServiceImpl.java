package dev.drf.pokedex.business.search;

import dev.drf.pokedex.api.business.SearchPokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.business.common.BusinessErrorCode;
import dev.drf.pokedex.common.api.ApiImpl;
import dev.drf.pokedex.common.api.CommonApiHandler;
import dev.drf.pokedex.common.error.PokedexException;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@ApiImpl
public class SearchPokemonApiServiceImpl extends CommonApiHandler implements SearchPokemonApiService {
    private static final System.Logger LOGGER = System.getLogger(SearchPokemonApiServiceImpl.class.getName());

    private final SearchByVersionService searchByVersionService;
    private final SearchByPokemonNameService searchByPokemonNameService;

    public SearchPokemonApiServiceImpl(SearchByVersionService searchByVersionService,
                                       SearchByPokemonNameService searchByPokemonNameService) {
        this.searchByVersionService = searchByVersionService;
        this.searchByPokemonNameService = searchByPokemonNameService;
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> getByVersion(long id,
                                           int version) {
        return safeExecute(() ->
                searchByVersionService.getByVersion(id, version)
                        .orElseThrow(() -> new PokedexException(BusinessErrorCode.POKEMON_NOT_FOUND,
                                String.format("Pokemon not found by id: %s and version: %s", id, version)))
        );
    }

    @Nonnull
    @Override
    public ApiResult<List<Pokemon>> searchByPokemonName(@Nullable String name,
                                                        @Nullable String title,
                                                        @Nullable String nickname) {
        return safeExecute(() -> searchByPokemonNameService.searchByPokemonName(name, title, nickname));
    }

    @Override
    protected System.Logger getLogger() {
        return LOGGER;
    }
}
