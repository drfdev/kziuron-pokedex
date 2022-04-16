package dev.drf.pokedex.api.common.async;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * Асинхронный результат, если API вызывается не синхронно, тогда стоит использовать данный класс
 *
 * @param <T> - тип результата
 */
public interface AsyncResult<T> {
    /**
     * Вернуть результат сразу как только он будет готов
     * Вызов метода блокирует вызовы следующих методов, пока результат операции не будет готов
     *
     * @param delay - задержка ожидания ответа
     * @param unit  - единица времени ожидания
     * @return результат операции
     */
    @Nullable
    T get(@Nonnegative long delay,
          @Nonnull TimeUnit unit);

    /**
     * Вернуть результат как он будет готов в колбек
     * Метод не являет блокирующим
     *
     * @param callback - колбек для ответа
     */
    void await(@Nonnull ApiCallback<T> callback);
}
