package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorizationScenarioContextBuilderTest {
    private final AuthorizationScenarioContextBuilder contextBuilder = new AuthorizationScenarioContextBuilder();

    @Test
    void shouldReturnCorrectContextType() {
        assertEquals(ContextType.AUTHORIZATION, contextBuilder.contextType());
    }

    @Test
    void shouldBuildContext_whenAnyParameterIsPassed() {
        // act
        ScenarioContext context = contextBuilder.build(new String[0]);

        // assert
        assertNotNull(context);
        assertEquals(AuthorizationContext.class, context.getClass());
    }
}
