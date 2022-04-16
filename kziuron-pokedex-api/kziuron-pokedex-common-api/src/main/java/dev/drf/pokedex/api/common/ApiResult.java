package dev.drf.pokedex.api.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Результат API
 *
 * @param status  - статус ответа
 * @param payload - объект ответа
 * @param errors  - ошибки возникшие во время работы
 * @param <T>     - тип возвращаемого объекта
 */
public record ApiResult<T>(@Nonnull ApiStatus status,
                           @Nullable T payload,
                           @Nonnull List<ApiError> errors) implements Serializable {
    public ApiResult(@Nonnull ApiStatus status,
                     @Nullable T payload,
                     @Nullable List<ApiError> errors) {
        this.status = requireNonNull(status);
        this.payload = payload;
        this.errors = errors == null
                ? Collections.emptyList()
                : errors;
    }

    @Nonnull
    public static <P> ApiResult<P> success(@Nullable P payload) {
        return new ApiResult<>(ApiStatus.SUCCESS, payload, null);
    }

    @Nonnull
    public static <P> ApiResult<P> error(@Nonnull ApiError apiError) {
        requireNonNull(apiError);
        return new ApiResult<>(ApiStatus.ERROR, null, Collections.singletonList(apiError));
    }

    @Nonnull
    public static <P> ApiResult<P> error(@Nonnull List<ApiError> errors) {
        requireNonNull(errors);
        return new ApiResult<>(ApiStatus.ERROR, null, errors);
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "status=" + status +
                ", payload=" + payload +
                ", errors=" + errors +
                '}';
    }
}
