package dev.drf.pokedex.api.core.dictionary;

import dev.drf.pokedex.api.common.Api;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.dictionary.Dictionary;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Api
public interface DictionaryApiService {
    <T extends Dictionary> ApiResult<Optional<T>> get(@Nonnull Class<T> clazz,
                                                      @Nonnegative long code);

    <T extends Dictionary> ApiResult<List<T>> getAll(@Nonnull Class<T> clazz);
}
