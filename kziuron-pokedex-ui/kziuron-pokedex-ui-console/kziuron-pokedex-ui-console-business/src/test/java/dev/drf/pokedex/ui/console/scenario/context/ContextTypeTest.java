package dev.drf.pokedex.ui.console.scenario.context;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContextTypeTest {
    @Test
    void shouldAuthorizationContextHasCorrectType() {
        // arrange
        AuthorizationContext context = new AuthorizationContext();

        // act - assert
        assertEquals(ContextType.AUTHORIZATION, context.contextType());
    }

    @Test
    void shouldModifyContextHasCorrectType() {
        // arrange
        ModifyContext context = ModifyContext.ofFile();

        // act - assert
        assertEquals(ContextType.MODIFY, context.contextType());
    }

    @Test
    void shouldSearchContextHasCorrectType() {
        // arrange
        SearchContext context = SearchContext.ofFile(Paths.get("test", "path"));

        // act - assert
        assertEquals(ContextType.SEARCH, context.contextType());
    }

    @Test
    void shouldMultipleAttemptsContextHasCorrectType() {
        // arrange
        MultipleAttemptsContext<?> context = MultipleAttemptsContext.of(SearchContext.ofFile(Paths.get("test", "path")), 10);

        // act - assert
        assertEquals(ContextType.SEARCH, context.contextType());
    }
}
