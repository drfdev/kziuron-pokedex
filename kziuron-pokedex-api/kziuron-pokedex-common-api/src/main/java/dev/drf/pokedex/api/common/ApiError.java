package dev.drf.pokedex.api.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * Ошибка при работе API
 *
 * @param errorCode - код ошибки
 * @param message   - сообщение об ошибке
 */
public record ApiError(@Nonnull ApiErrorCode errorCode,
                       @Nullable String message) implements Serializable {
    public ApiError(@Nonnull ApiErrorCode errorCode,
                    @Nullable String message) {
        this.errorCode = requireNonNull(errorCode);
        this.message = message;
    }

    @Nonnull
    public static ApiError of(@Nonnull ApiErrorCode errorCode, @Nonnull String message) {
        requireNonNull(message);
        return new ApiError(errorCode, message);
    }

    @Nonnull
    public static ApiError of(@Nonnull ApiErrorCode errorCode) {
        return new ApiError(errorCode, null);
    }

    @Override
    public String toString() {
        return "ApiError{" + "errorCode=" + errorCode + ", message='" + message + '\'' + '}';
    }
}
