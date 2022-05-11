package dev.drf.pokedex.ui.console.scenario;

import dev.drf.pokedex.ui.console.error.ErrorCodes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record ScenarioError(@Nonnull String code,
                            @Nullable String message) {

    public static ScenarioError of(@Nonnull String code,
                                   @Nullable String message) {
        return new ScenarioError(code, message);
    }

    public static ScenarioError of(@Nonnull ErrorCodes code) {
        return new ScenarioError(code.getCode(), null);
    }

    public static ScenarioError of(@Nonnull ErrorCodes code,
                                   @Nullable String message) {
        return new ScenarioError(code.getCode(), message);
    }
}
