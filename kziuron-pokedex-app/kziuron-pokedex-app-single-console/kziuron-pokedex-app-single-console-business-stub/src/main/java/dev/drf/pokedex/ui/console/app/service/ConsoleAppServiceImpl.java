package dev.drf.pokedex.ui.console.app.service;

import dev.drf.pokedex.ui.console.AuthorizationService;
import dev.drf.pokedex.ui.console.CommandDetector;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.ScenarioExecutor;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.result.AuthorizationToken;

import javax.annotation.Nonnull;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.AUTHORIZATION_FAILED;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_COMMAND;

public class ConsoleAppServiceImpl implements ConsoleAppService {
    private static final System.Logger LOGGER = System.getLogger(ConsoleAppServiceImpl.class.getName());
    private static final String STOP_WORD = "stop";

    private boolean stopFlag = false;

    private final ConsoleService consoleService;
    private final CommandDetector commandDetector;
    private final AuthorizationService authorizationService;
    private final ScenarioExecutor scenarioExecutor;

    public ConsoleAppServiceImpl(ConsoleService consoleService,
                                 CommandDetector commandDetector,
                                 AuthorizationService authorizationService,
                                 ScenarioExecutor scenarioExecutor) {
        this.consoleService = consoleService;
        this.commandDetector = commandDetector;
        this.authorizationService = authorizationService;
        this.scenarioExecutor = scenarioExecutor;
    }

    @Override
    public void runConsole() {
        try {
            var token = authorize();
            mainCommandLoop(token);
        } catch (ConsoleUIException ex) {
            LOGGER.log(System.Logger.Level.ERROR, "Console error with code", ex.getErrorCode(), ex);
        } catch (Exception ex) {
            LOGGER.log(System.Logger.Level.ERROR, "Unexpected error", ex);
        } finally {
            stopConsole();
        }
    }

    @Override
    public void stopConsole() {
        this.stopFlag = true;
    }

    @Nonnull
    private AuthorizationToken authorize() {
        consoleService.write("At first, you need to authorize");
        consoleService.write("user:");
        var user = consoleService.read();
        consoleService.write("password:");
        var password = consoleService.read();

        if (user == null || password == null) {
            throw new ConsoleUIException(AUTHORIZATION_FAILED, "login or password are null");
        }

        return authorizationService.authorize(user, password)
                .orElseThrow(() -> new ConsoleUIException(AUTHORIZATION_FAILED, "Not authorized"));
    }

    private void mainCommandLoop(@Nonnull AuthorizationToken token) {
        var userName = token.user();
        consoleService.write("Welcome " + userName);
        consoleService.write("If you want to stop, type 'stop'");

        while (!stopFlag) {
            consoleService.write("Input command:");

            var printedValue = consoleService.read();
            if (printedValue == null) {
                throw new ConsoleUIException(UNKNOWN_COMMAND, "Command is null");
            }
            if (STOP_WORD.equals(printedValue)) {
                consoleService.write("Finished");
                stopConsole();
                break;
            }

            var context = commandDetector.detect(printedValue);
            var result = scenarioExecutor.execute(context);

            consoleService.write("Command result: " + result);
        }
    }
}
