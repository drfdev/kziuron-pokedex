package dev.drf.pokedex.api.common;

import javax.annotation.Nonnull;
import java.io.Serializable;

public interface ApiErrorCode extends Serializable {
    @Nonnull
    String getErrorCode();
}
