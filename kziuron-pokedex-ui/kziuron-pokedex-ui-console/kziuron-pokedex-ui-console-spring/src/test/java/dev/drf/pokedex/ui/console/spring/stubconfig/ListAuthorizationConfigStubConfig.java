package dev.drf.pokedex.ui.console.spring.stubconfig;

import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ListAuthorizationConfigStubConfig {
    private static List<ImmutablePair<String, String>> auth;

    /**
     * Инициализация логинов и паролей, для тестов
     */
    public static void initialize(List<ImmutablePair<String, String>> auth) {
        ListAuthorizationConfigStubConfig.auth = auth;
    }

    @Bean
    public ListAuthorizationConfig listAuthorizationConfig() {
        ListAuthorizationConfig config = Mockito.mock(ListAuthorizationConfig.class);
        Mockito.when(config.authList()).thenReturn(auth);
        return config;
    }
}
