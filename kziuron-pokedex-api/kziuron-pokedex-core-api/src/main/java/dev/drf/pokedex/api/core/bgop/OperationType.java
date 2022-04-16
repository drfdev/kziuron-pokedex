package dev.drf.pokedex.api.core.bgop;

import javax.annotation.Nonnull;
import java.io.Serializable;

public interface OperationType extends Serializable {
    @Nonnull
    String getOperationType();
}
