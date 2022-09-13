package dev.drf.pokedex.common.error;

import javax.annotation.Nonnull;
import java.io.Serial;

import static java.util.Objects.requireNonNull;

public final class PokedexException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6174561105589944355L;

    private final ErrorCode errorCode;

    public PokedexException(@Nonnull ErrorCode errorCode) {
        this.errorCode = requireNonNull(errorCode);
    }

    public PokedexException(@Nonnull ErrorCode errorCode,
                            @Nonnull String message) {
        super(message);
        this.errorCode = requireNonNull(errorCode);
    }

    public PokedexException(@Nonnull ErrorCode errorCode,
                            @Nonnull String message,
                            @Nonnull Throwable cause) {
        super(message, cause);
        this.errorCode = requireNonNull(errorCode);
    }

    public PokedexException(@Nonnull ErrorCode errorCode,
                            @Nonnull Throwable cause) {
        super(cause);
        this.errorCode = requireNonNull(errorCode);
    }

    @Nonnull
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
