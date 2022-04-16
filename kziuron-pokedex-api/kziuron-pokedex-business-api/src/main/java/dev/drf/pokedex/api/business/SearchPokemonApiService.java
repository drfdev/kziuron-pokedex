package dev.drf.pokedex.api.business;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Api
public interface SearchPokemonApiService {
    /**
     * Поиск покемона по ID и версии
     *
     * @param id      записи
     * @param version записи
     * @return результат поиска
     */
    @Nonnull
    ApiResult<Pokemon> getByVersion(long id,
                                    @Nonnegative int version);

    /**
     * Поиск покемона по имени
     *
     * @param name     имя
     * @param title    титл
     * @param nickname кличка
     * @return список найденных покемонов
     */
    @Nonnull
    ApiResult<List<Pokemon>> searchByPokemonName(@Nullable String name,
                                                 @Nullable String title,
                                                 @Nullable String nickname);
}
