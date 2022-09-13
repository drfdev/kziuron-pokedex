package dev.drf.pokedex.business.pokemon;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.business.common.BusinessErrorCode;
import dev.drf.pokedex.common.api.ApiImpl;
import dev.drf.pokedex.common.api.CommonApiHandler;
import dev.drf.pokedex.common.error.PokedexException;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

@ApiImpl
public class PokemonApiServiceImpl extends CommonApiHandler implements PokemonApiService {
    private static final System.Logger LOGGER = System.getLogger(PokemonApiServiceImpl.class.getName());

    private final PokemonGetService pokemonGetService;
    private final PokemonCreateService pokemonCreateService;
    private final PokemonDeactivateService pokemonDeactivateService;
    private final PokemonUpdateService pokemonUpdateService;
    private final PokemonSmartUpdateService pokemonSmartUpdateService;

    public PokemonApiServiceImpl(PokemonGetService pokemonGetService,
                                 PokemonCreateService pokemonCreateService,
                                 PokemonDeactivateService pokemonDeactivateService,
                                 PokemonUpdateService pokemonUpdateService,
                                 PokemonSmartUpdateService pokemonSmartUpdateService) {
        this.pokemonGetService = pokemonGetService;
        this.pokemonCreateService = pokemonCreateService;
        this.pokemonDeactivateService = pokemonDeactivateService;
        this.pokemonUpdateService = pokemonUpdateService;
        this.pokemonSmartUpdateService = pokemonSmartUpdateService;
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> get(long id) {
        return safeExecute(() ->
                pokemonGetService.get(id)
                        .orElseThrow(() -> new PokedexException(BusinessErrorCode.POKEMON_NOT_FOUND,
                                String.format("Pokemon not found by id: %s", id)))
        );
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> create(@Nonnull Pokemon pokemon) {
        return safeExecute(() -> pokemonCreateService.create(pokemon));
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> deactivate(@Nonnull Pokemon pokemon) {
        return safeExecute(() -> pokemonDeactivateService.deactivate(pokemon));
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> update(@Nonnull Pokemon pokemon) {
        return safeExecute(() -> pokemonUpdateService.update(pokemon));
    }

    @Nonnull
    @Override
    public ApiResult<Pokemon> smartUpdate(@Nonnull Pokemon pokemon) {
        return safeExecute(() -> pokemonSmartUpdateService.smartUpdate(pokemon));
    }

    @Override
    protected System.Logger getLogger() {
        return LOGGER;
    }
}
