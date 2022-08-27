package dev.drf.pokedex.ui.console.spring.auth;

import com.google.common.collect.ImmutableList;
import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;
import dev.drf.pokedex.ui.console.spring.stubconfig.ListAuthorizationConfigStubConfig;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ListAuthorizationConfiguration.class,
        ListAuthorizationConfigStubConfig.class
})
class ListAuthorizationConfigurationTest {
    private static final List<ImmutablePair<String, String>> AUTHS = ImmutableList.of(
            ImmutablePair.of("login", "password"),
            ImmutablePair.of("Mikasa", "pokemon"),
            ImmutablePair.of("Pika-Pika", "Qwerty123")
    );
    @Autowired
    private ListAuthorizationConfig config;

    @Autowired
    private AuthorizationService service;

    @BeforeAll
    public static void setUp() {
        ListAuthorizationConfigStubConfig.initialize(AUTHS);
    }

    @Test
    void shouldAuthorize_whenCorrectLoginAndPassword() {
        // arrange
        Mockito.when(config.ignoreCase()).thenReturn(false);

        String login = "Mikasa";
        String password = "pokemon";

        // act
        Optional<AuthorizationToken> auth = service.authorize(login, password);

        // assert
        assertTrue(auth.isPresent());
        AuthorizationToken token = auth.get();
        assertEquals("Mikasa", token.user());
    }

    @Test
    void shouldAuthorize_whenWrongCaseLoginAndCorrectPasswordAndIgnoreCaseEnabled() {
        // arrange
        Mockito.when(config.ignoreCase()).thenReturn(true);

        String login = "LOGIN";
        String password = "password";

        // act
        Optional<AuthorizationToken> auth = service.authorize(login, password);

        // assert
        assertTrue(auth.isPresent());
        AuthorizationToken token = auth.get();
        assertEquals("login", token.user());
    }

    @Test
    void shouldNotAuthorize_whenCorrectLoginAndIncorrectPassword() {
        // arrange
        Mockito.when(config.ignoreCase()).thenReturn(false);

        String login = "Mikasa";
        String password = "UNKNOWN-PASSWORD";

        // act
        Optional<AuthorizationToken> auth = service.authorize(login, password);

        // assert
        assertTrue(auth.isEmpty());
    }

    @Test
    void shouldNotAuthorize_whenLoginAndPasswordNotMatch() {
        // arrange
        Mockito.when(config.ignoreCase()).thenReturn(false);

        String login = "Pika-Pika";
        String password = "password";

        // act
        Optional<AuthorizationToken> auth = service.authorize(login, password);

        // assert
        assertTrue(auth.isEmpty());
    }
}
