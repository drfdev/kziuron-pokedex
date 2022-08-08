package dev.drf.pokedex.ui.console.auth.config;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.List;

public interface ListAuthorizationConfig {
    @Nonnull
    List<ImmutablePair<String, String>> authList();

    boolean ignoreCase();
}
