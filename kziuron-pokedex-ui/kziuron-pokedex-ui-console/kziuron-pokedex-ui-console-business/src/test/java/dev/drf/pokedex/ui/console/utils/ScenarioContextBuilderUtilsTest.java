package dev.drf.pokedex.ui.console.utils;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioContextBuilderUtilsTest {
    private final String[] lineParams = new String[]{"-test", "-name=abc", "-P", "mode1"};

    @Test
    void shouldReturnTrue_whenLineParamsContainsParam() {
        // arrange
        String paramName = "-test";

        // act
        boolean result = ScenarioContextBuilderUtils.hasParameter(lineParams, paramName);

        // assert
        assertTrue(result);
    }

    @Test
    void shouldReturnTrue_whenLineParamsContainsParamWithValueInSingleValue() {
        // arrange
        String paramName = "-name";

        // act
        boolean result = ScenarioContextBuilderUtils.hasParameter(lineParams, paramName);

        // assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenLineParamsIsEmpty() {
        // arrange
        String paramName = "-test";

        // act
        boolean result = ScenarioContextBuilderUtils.hasParameter(new String[]{}, paramName);

        // assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenLineParamsNotContainsParamName() {
        // arrange
        String paramName = "-unknown";

        // act
        boolean result = ScenarioContextBuilderUtils.hasParameter(lineParams, paramName);

        // assert
        assertFalse(result);
    }

    @Test
    void shouldNotFoundValue_whenLineParamsNotContainParamWithName() {
        // arrange
        String paramName = "-unknown";

        // act
        Optional<String> result = ScenarioContextBuilderUtils.getParameterValue(lineParams, paramName);

        // assert
        assertFalse(result.isPresent());
    }

    @Test
    void shouldNotFoundValue_whenLineParamsIsEmpty() {
        // arrange
        String paramName = "-name";

        // act
        Optional<String> result = ScenarioContextBuilderUtils.getParameterValue(new String[]{}, paramName);

        // assert
        assertFalse(result.isPresent());
    }

    @Test
    void shouldFoundValue_whenLineParamsContainParamWithComaSeparated() {
        // arrange
        String paramName = "-name";

        // act
        Optional<String> result = ScenarioContextBuilderUtils.getParameterValue(lineParams, paramName);

        // assert
        assertTrue(result.isPresent());

        String value = result.get();
        assertEquals("abc", value);
    }

    @Test
    void shouldFoundValue_whenLineParamsContainParamWithSpaceSeparated() {
        // arrange
        String paramName = "-P";

        // act
        Optional<String> result = ScenarioContextBuilderUtils.getParameterValue(lineParams, paramName);

        // assert
        assertTrue(result.isPresent());

        String value = result.get();
        assertEquals("mode1", value);
    }
}
