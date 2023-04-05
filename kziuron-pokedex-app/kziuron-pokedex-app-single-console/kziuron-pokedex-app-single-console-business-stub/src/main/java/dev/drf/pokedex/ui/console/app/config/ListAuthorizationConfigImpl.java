package dev.drf.pokedex.ui.console.app.config;

import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class ListAuthorizationConfigImpl implements ListAuthorizationConfig {
    private final boolean ignoreCase;
    private final List<ImmutablePair<String, String>> authList;

    public ListAuthorizationConfigImpl(ListAuthorizationRawConfig rawConfig) {
        this.ignoreCase = rawConfig.isIgnoreCase();
        this.authList = List.copyOf(
                rawConfig.getUsers().stream()
                        .map(it -> ImmutablePair.of(it.getLogin(), it.getPassword()))
                        .collect(Collectors.toList())
        );
    }

    @Nonnull
    @Override
    public List<ImmutablePair<String, String>> authList() {
        return this.authList;
    }

    @Override
    public boolean ignoreCase() {
        return this.ignoreCase;
    }
}
