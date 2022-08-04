package dev.drf.pokedex.ui.console.utils;

import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.api.common.ApiStatus;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NULL_API_RESULT;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_ERROR;

/**
 * Утилита для обработки результатов работы
 */
public final class ScenarioResultUtils {
    private ScenarioResultUtils() {
    }

    @Nonnull
    public static <P> ScenarioResult<P> toScenarioResult(@Nonnull ApiResult<P> apiResult) {
        if (apiResult.status() == ApiStatus.SUCCESS) {
            return apiResult.payload() != null
                    ? ScenarioResult.success(apiResult.payload())
                    : ScenarioResult.error(ScenarioError.of(NULL_API_RESULT));
        }

        return ScenarioResult.error(toError(apiResult.errors()));
    }

    @Nonnull
    private static ScenarioError toError(@Nonnull List<ApiError> errors) {
        return errors.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(it -> ScenarioError.of(it.errorCode().getErrorCode(), it.message()))
                .orElseGet(() -> ScenarioError.of(UNKNOWN_ERROR));
    }
}
