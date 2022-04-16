package dev.drf.pokedex.api.common.async;

import javax.annotation.Nonnull;

/**
 * Колбек для вызова API
 *
 * @param <T> - тип возвращаемого значения
 */
public interface ApiCallback<T> {
    /**
     * Успешный результат
     *
     * @param result - результат работы API
     */
    void callback(@Nonnull T result);

    /**
     * Неуспешный результат
     *
     * @param error - ошибка вызова
     */
    void failure(@Nonnull Throwable error);
}
