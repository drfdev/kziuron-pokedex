package dev.drf.pokedex.ui.console.auth.list;

import com.google.common.collect.ImmutableMap;
import dev.drf.pokedex.common.mark.NeedToImplement;
import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.auth.config.ListAuthorizationConfig;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NeedToImplement(classes = {
        ListAuthorizationConfig.class
})
public class ListAuthorizationService implements AuthorizationService {
    private static final System.Logger LOGGER = System.getLogger(ListAuthorizationService.class.getName());

    private final ListAuthorizationConfig config;
    private final Map<String, String> authMap;

    public ListAuthorizationService(ListAuthorizationConfig config) {
        this.config = config;
        this.authMap = ImmutableMap.copyOf(
                config.authList().stream()
                        .collect(Collectors.toMap(
                                it -> toCorrectCase(it.getKey()),
                                Pair::getValue))
        );
    }

    @Nonnull
    @Override
    public Optional<AuthorizationToken> authorize(@Nonnull String login,
                                                  @Nonnull String password) {
        String testKey = toCorrectCase(login);

        if (authMap.containsKey(testKey)) {
            String authPassword = authMap.get(testKey);

            if (Objects.equals(authPassword, password)) {
                AuthorizationToken token = new AuthorizationToken(testKey);
                return Optional.of(token);
            }
        }

        return Optional.empty();
    }

    @Nonnull
    private String toCorrectCase(@Nonnull String key) {
        return config.ignoreCase()
                ? StringUtils.lowerCase(key)
                : key;
    }
}
