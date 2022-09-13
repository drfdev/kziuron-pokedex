package dev.drf.pokedex.common.api;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ApiExecutable<R> {
    @Nonnull
    R execute();
}
