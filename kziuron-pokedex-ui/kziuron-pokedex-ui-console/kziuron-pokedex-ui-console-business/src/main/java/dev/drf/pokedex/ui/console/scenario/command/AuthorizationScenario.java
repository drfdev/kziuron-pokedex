package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.context.AuthorizationContext;
import dev.drf.pokedex.ui.console.scenario.context.MultipleAttemptsContext;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;

import javax.annotation.Nonnull;
import java.util.Optional;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.AUTHORIZATION_FAILED;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.NULL_LOGIN_PASSWORD;

public class AuthorizationScenario extends AbstractMultipleAttemptsScenario<AuthorizationContext, AuthorizationToken> {
    private static final int DEFAULT_ATTEMPTS_COUNT = 3;

    private final AuthorizationService authorizationService;
    private final ConsoleService consoleService;

    public AuthorizationScenario(AuthorizationService authorizationService,
                                 ConsoleService consoleService) {
        this.authorizationService = authorizationService;
        this.consoleService = consoleService;
    }

    @Nonnull
    @Override
    protected ScenarioResult<AuthorizationToken> safeExecute(@Nonnull AuthorizationContext context) {
        MultipleAttemptsContext<AuthorizationContext> multipleContext = MultipleAttemptsContext.of(context, DEFAULT_ATTEMPTS_COUNT);
        return multipleExecute(multipleContext);
    }

    @Nonnull
    @Override
    public Command key() {
        return Commands.AUTHORIZATION;
    }

    @Override
    protected ScenarioResult<AuthorizationToken> singleAttempt(@Nonnull AuthorizationContext context,
                                                               int attempt) {
        consoleService.write(String.format("Authorization, attempt %s from %s", (attempt + 1), DEFAULT_ATTEMPTS_COUNT));
        // login
        consoleService.write("login: ");
        String login = consoleService.read();
        // password
        consoleService.write("password: ");
        String password = consoleService.read();

        if (login == null || password == null) {
            return ScenarioResult.error(ScenarioError.of(NULL_LOGIN_PASSWORD, "Login or password is null"));
        }

        Optional<AuthorizationToken> token = authorizationService.authorize(login, password);

        return token
                .map(ScenarioResult::success)
                .orElseGet(() -> ScenarioResult.error(ScenarioError.of(AUTHORIZATION_FAILED, "Authorization failed")));
    }
}
