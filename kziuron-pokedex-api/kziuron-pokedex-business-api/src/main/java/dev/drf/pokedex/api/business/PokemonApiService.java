package dev.drf.pokedex.api.business;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;

@Api
public interface PokemonApiService {
    /**
     * Получение покемона по ID
     *
     * @param id записи
     * @return покемон
     */
    @Nonnull
    ApiResult<Pokemon> get(long id);

    /**
     * Создание нового покемона
     *
     * @param pokemon новые данные покемона
     * @return созданная запись
     */
    @Nonnull
    ApiResult<Pokemon> create(@Nonnull Pokemon pokemon);

    /**
     * Деактивация покемона, т.к. нет удаления
     *
     * @param pokemon запись которую нужно деактивировать
     * @return деактивированная запись
     */
    @Nonnull
    ApiResult<Pokemon> deactivate(@Nonnull Pokemon pokemon);

    /**
     * Обновление записи
     *
     * @param pokemon запись которую нужно обновить
     * @return результат обновления
     */
    @Nonnull
    ApiResult<Pokemon> update(@Nonnull Pokemon pokemon);

    /**
     * "Умное" обновление
     * В отличие от обычного обновления тут идет сравнение не по ID блоков, а по данным
     *
     * @param pokemon запись которую нужно обновить
     * @return результат обновления
     */
    @Nonnull
    ApiResult<Pokemon> smartUpdate(@Nonnull Pokemon pokemon);
}
