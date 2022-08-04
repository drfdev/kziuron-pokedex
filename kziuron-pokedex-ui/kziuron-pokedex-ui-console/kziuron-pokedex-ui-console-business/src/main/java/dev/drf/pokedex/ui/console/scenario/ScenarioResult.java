package dev.drf.pokedex.ui.console.scenario;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public record ScenarioResult<R>(@Nonnull ScenarioStatus status,
                                @Nullable R payload,
                                @Nullable ScenarioError error) {

    public static <P> ScenarioResult<P> success(@Nonnull P payload) {
        return new ScenarioResult<>(ScenarioStatus.SUCCESS, payload, null);
    }

    public static <P> ScenarioResult<P> error(@Nonnull ScenarioError error) {
        requireNonNull(error);
        return new ScenarioResult<>(ScenarioStatus.ERROR, null, error);
    }
}
