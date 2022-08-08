package dev.drf.pokedex.ui.console.spring.auth;

import dev.drf.pokedex.common.mark.NeedToImplement;
import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import dev.drf.pokedex.ui.console.auth.list.ListAuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NeedToImplement(classes = {
        ListAuthorizationConfig.class
})
public class ListAuthorizationConfiguration {
    @Bean
    public AuthorizationService authorizationService(ListAuthorizationConfig config) {
        return new ListAuthorizationService(config);
    }
}
