package dev.drf.pokedex.api.core.bgop;

import dev.drf.pokedex.model.Pokemon;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public record BackgroundOperation(@Nonnull OperationType operationType,
                                  @Nonnull Pokemon pokemon) implements Serializable {
    public BackgroundOperation(@Nonnull OperationType operationType,
                               @Nonnull Pokemon pokemon) {
        this.operationType = requireNonNull(operationType);
        this.pokemon = requireNonNull(pokemon);
    }

    @Nonnull
    public static BackgroundOperation of(@Nonnull OperationType operationType,
                                         @Nonnull Pokemon pokemon) {
        return new BackgroundOperation(operationType, pokemon);
    }

    @Override
    public String toString() {
        return "BackgroundOperation{" +
                "operationType=" + operationType +
                ", pokemon=" + pokemon +
                '}';
    }
}
