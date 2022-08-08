package dev.drf.pokedex.ui.console.auth.list;

import com.google.common.collect.Lists;
import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListAuthorizationServiceTest {
    @Mock
    private ListAuthorizationConfig config;

    private ListAuthorizationService service;

    @BeforeEach
    public void resetConfig() {
        Mockito.reset(config);
    }

    private void initializeService(@Nonnull List<ImmutablePair<String, String>> auth,
                                   boolean ignoreCase) {
        Mockito.when(config.authList()).thenReturn(auth);
        Mockito.when(config.ignoreCase()).thenReturn(ignoreCase);

        service = new ListAuthorizationService(config);
    }

    @Test
    void shouldAuth_whenLoginPasswordCorrect() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("test-login", "test-password"));
        initializeService(auth, false);

        // act
        Optional<AuthorizationToken> token = service.authorize("test-login", "test-password");

        // assert
        assertTrue(token.isPresent());
        assertEquals("test-login", token.get().user());
    }

    @Test
    void shouldNotAuth_whenPasswordIncorrect() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("test-login", "test-password"));
        initializeService(auth, false);

        // act
        Optional<AuthorizationToken> token = service.authorize("test-login", "wrong-password");

        // assert
        assertFalse(token.isPresent());
    }

    @Test
    void shouldNotAuth_whenUnknownLogin() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("test-login", "test-password"));
        initializeService(auth, false);

        // act
        Optional<AuthorizationToken> token = service.authorize("unknown-login", "test-password");

        // assert
        assertFalse(token.isPresent());
    }

    @Test
    void shouldAuth_whenLoginPasswordCorrectAndCaseSensitive() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("CASE-login-VALUE", "test-password"));
        initializeService(auth, false);

        // act
        Optional<AuthorizationToken> token = service.authorize("CASE-login-VALUE", "test-password");

        // assert
        assertTrue(token.isPresent());
        assertEquals("CASE-login-VALUE", token.get().user());
    }

    @Test
    void shouldNotAuth_whenCaseSensitiveAndLoginWithWrongCase() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("CASE-login-VALUE", "test-password"));
        initializeService(auth, false);

        // act
        Optional<AuthorizationToken> token = service.authorize("case-login-value", "test-password");

        // assert
        assertFalse(token.isPresent());
    }

    @Test
    void shouldAuth_whenLoginPasswordCorrectAndCaseIgnored() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("CASE-login-VALUE", "test-password"));
        initializeService(auth, true);

        // act
        Optional<AuthorizationToken> token = service.authorize("CASE-login-VALUE", "test-password");

        // assert
        assertTrue(token.isPresent());
        assertEquals("case-login-value", token.get().user());
    }

    @Test
    void shouldAuth_whenCaseIgnoredAndLoginWithWrongCase() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("CASE-login-VALUE", "test-password"));
        initializeService(auth, true);

        // act
        Optional<AuthorizationToken> token = service.authorize("case-login-value", "test-password");

        // assert
        assertTrue(token.isPresent());
        assertEquals("case-login-value", token.get().user());
    }

    @Test
    void shouldAuth_whenLoginPasswordCorrectAndSeveralLoginPairs() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("login-1", "password-1"),
                ImmutablePair.of("login-2", "password-2"),
                ImmutablePair.of("login-3", "password-3"));
        initializeService(auth, true);

        // act
        Optional<AuthorizationToken> token = service.authorize("login-2", "password-2");

        // assert
        assertTrue(token.isPresent());
        assertEquals("login-2", token.get().user());
    }

    @Test
    void shouldNotAuth_whenSeveralLoginPairsAndLoginPasswordFromDifferentPairs() {
        // arrange
        List<ImmutablePair<String, String>> auth = Lists.newArrayList(
                ImmutablePair.of("login-1", "password-1"),
                ImmutablePair.of("login-2", "password-2"),
                ImmutablePair.of("login-3", "password-3"));
        initializeService(auth, true);

        // act
        Optional<AuthorizationToken> token = service.authorize("login-1", "password-3");

        // assert
        assertFalse(token.isPresent());
    }
}
