package dev.drf.pokedex.ui.console.utils;

import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiErrorCode;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.io.Serial;
import java.util.Arrays;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NULL_API_RESULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScenarioResultUtilsTest {
    private static ApiErrorCode newApiErrorCode(String code) {
        return new ApiErrorCode() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Nonnull
            @Override
            public String getErrorCode() {
                return code;
            }
        };
    }

    @Test
    void shouldReturnSuccessResult_whenSuccessApiResultWithPayload() {
        // arrange
        ApiResult<String> apiResult = ApiResult.success("correct-value");

        // act
        ScenarioResult<String> result = ScenarioResultUtils.toScenarioResult(apiResult);

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.SUCCESS, result.status());
        assertEquals("correct-value", result.payload());
    }

    @Test
    void shouldReturnErrorResult_whenSuccessApiResultWithNoPayload() {
        // arrange
        ApiResult<String> apiResult = ApiResult.success(null);

        // act
        ScenarioResult<String> result = ScenarioResultUtils.toScenarioResult(apiResult);

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals(NULL_API_RESULT.getCode(), result.error().code());
    }

    @Test
    void shouldReturnErrorResult_whenErrorApiResultWithSingleError() {
        // arrange
        ApiErrorCode errorCode = newApiErrorCode("error-code");
        ApiResult<String> apiResult = ApiResult.error(ApiError.of(errorCode, "message-error"));

        // act
        ScenarioResult<String> result = ScenarioResultUtils.toScenarioResult(apiResult);

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("error-code", result.error().code());
        assertEquals("message-error", result.error().message());
    }

    @Test
    void shouldReturnErrorResult_whenErrorApiResultWithSeveralErrors() {
        // arrange
        ApiErrorCode errorCode1 = newApiErrorCode("error-code-1");
        ApiErrorCode errorCode2 = newApiErrorCode("error-code-2");
        ApiResult<String> apiResult = ApiResult.error(Arrays.asList(
                ApiError.of(errorCode1, "message-error-1"),
                ApiError.of(errorCode2, "message-error-2")
        ));

        // act
        ScenarioResult<String> result = ScenarioResultUtils.toScenarioResult(apiResult);

        // assert
        assertNotNull(result);
        assertEquals(ScenarioStatus.ERROR, result.status());
        assertNotNull(result.error());
        assertEquals("error-code-1", result.error().code());
        assertEquals("message-error-1", result.error().message());
    }
}
