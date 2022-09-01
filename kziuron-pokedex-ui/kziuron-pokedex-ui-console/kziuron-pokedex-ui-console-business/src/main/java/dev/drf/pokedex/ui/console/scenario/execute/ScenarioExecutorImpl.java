package dev.drf.pokedex.ui.console.scenario.execute;

import com.google.common.collect.ImmutableMap;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.Scenario;
import dev.drf.pokedex.ui.console.ScenarioExecutor;
import dev.drf.pokedex.ui.console.command.CommandContext;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_SCENARIO_FOR_COMMAND;

public class ScenarioExecutorImpl implements ScenarioExecutor {
    private static final System.Logger LOGGER = System.getLogger(ScenarioExecutorImpl.class.getName());

    private final Map<Command, Scenario<ScenarioContext, ?>> scenarioMap;

    public ScenarioExecutorImpl(List<Scenario<? extends ScenarioContext, ?>> scenarioList) {
        this.scenarioMap = ImmutableMap.copyOf(
                scenarioList.stream()
                        .collect(Collectors.toMap(Scenario::key,
                                it -> (Scenario<ScenarioContext, ?>) it)));
    }

    @Nonnull
    @Override
    public <R> ScenarioResult<R> execute(@Nonnull CommandContext context) {
        Command command = context.command();
        Scenario<ScenarioContext, R> scenario = (Scenario<ScenarioContext, R>) scenarioMap.get(command);

        if (scenario == null) {
            LOGGER.log(System.Logger.Level.ERROR, "Scenario not found for command: {}", command);
            throw new ConsoleUIException(NO_SCENARIO_FOR_COMMAND, "Scenario not exists for command: " + command);
        }

        ScenarioContext scenarioContext = context.parameters();
        return scenario.execute(scenarioContext);
    }
}
