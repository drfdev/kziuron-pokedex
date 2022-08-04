package dev.drf.pokedex.ui.console.command;

import com.google.common.collect.ImmutableMap;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.CommandDetector;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_COMMAND;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_CONTEXT_TYPE;

public class CommandDetectorImpl implements CommandDetector {
    private static final String SPLIT_PATTERN = "\\s+";
    private static final String[] EMPTY = new String[0];

    private final Map<String, Command> commands;
    private final Map<ContextType, ScenarioContextBuilder> scenarioContextBuilders;

    private final ContextTypeDetector contextTypeDetector;

    public CommandDetectorImpl(ContextTypeDetector contextTypeDetector,
                               List<ScenarioContextBuilder> scenarioContextBuilders) {
        this.contextTypeDetector = contextTypeDetector;

        this.commands = ImmutableMap.copyOf(
                Arrays.stream(Commands.values())
                        .collect(Collectors.toMap(Commands::key, Function.identity())));
        this.scenarioContextBuilders = ImmutableMap.copyOf(
                scenarioContextBuilders.stream()
                        .collect(Collectors.toMap(ScenarioContextBuilder::contextType, Function.identity())));
    }

    @Nonnull
    @Override
    public CommandContext detect(@Nonnull String consoleText) {
        String[] vals = splitConsoleText(consoleText);
        String commandName = vals[0];
        Command command = commands.get(commandName);

        if (command == null) {
            throw new ConsoleUIException(UNKNOWN_COMMAND, "Unknown command, name: " + commandName);
        }

        ContextType contextType = contextTypeDetector.detect(command);
        ScenarioContextBuilder scenarioContextBuilder = scenarioContextBuilders.get(contextType);

        if (scenarioContextBuilder == null) {
            throw new ConsoleUIException(UNKNOWN_CONTEXT_TYPE, "Unknown context type: " + contextType);
        }

        String[] params = getParams(vals);
        ScenarioContext parameters = scenarioContextBuilder.build(params);

        return new CommandContext(command, parameters);
    }

    @Nonnull
    private String[] splitConsoleText(@Nonnull String consoleText) {
        String preparedText = StringUtils.trimToEmpty(consoleText);
        return preparedText.split(SPLIT_PATTERN);
    }

    @Nonnull
    private String[] getParams(@Nonnull String[] vals) {
        if (vals.length == 1) {
            return EMPTY;
        }

        String[] params = new String[vals.length - 1];
        System.arraycopy(vals, 1, params, 0, params.length);
        return params;
    }
}
