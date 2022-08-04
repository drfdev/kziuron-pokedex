package dev.drf.pokedex.ui.console.error;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serial;

import static java.util.Objects.requireNonNull;

/**
 * Внутренняя ошибка для консольного UI.
 * Включает в себя обязательный код ошибки
 */
public class ConsoleUIException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4652448469712544407L;

    private final ErrorCodes errorCode;

    public ConsoleUIException(@Nonnull ErrorCodes errorCode) {
        this.errorCode = requireNonNull(errorCode);
    }

    public ConsoleUIException(@Nonnull ErrorCodes errorCode,
                              @Nullable String message) {
        super(message);
        this.errorCode = requireNonNull(errorCode);
    }

    public ConsoleUIException(@Nonnull ErrorCodes errorCode,
                              @Nullable String message,
                              @Nullable Throwable cause) {
        super(message, cause);
        this.errorCode = requireNonNull(errorCode);
    }

    public ConsoleUIException(@Nonnull ErrorCodes errorCode,
                              @Nullable Throwable cause) {
        super(cause);
        this.errorCode = requireNonNull(errorCode);
    }

    @Nonnull
    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ConsoleUIException{" +
                "errorCode=" + errorCode +
                "} " + super.toString();
    }
}
