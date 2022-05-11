package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface AuthorizationService {
    @Nonnull
    Optional<AuthorizationToken> authorize(@Nonnull String login,
                                           @Nonnull String password);
}
