package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.command.ScenarioContextBuilder;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;

import javax.annotation.Nonnull;
import java.nio.file.Paths;
import java.util.Optional;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_REQUIRED_PARAMETER;
import static dev.drf.pokedex.ui.console.utils.ScenarioContextBuilderUtils.*;

public class SearchScenarioContextBuilder implements ScenarioContextBuilder {
    @Nonnull
    @Override
    public ScenarioContext build(@Nonnull String[] params) {
        boolean isConsole = hasParameter(params, PARAM_CONSOLE);
        boolean isFile = hasParameter(params, PARAM_FILE);

        if (isConsole) {
            return SearchContext.ofConsole();
        } else if (isFile) {
            Optional<String> pathValue = getParameterValue(params, PARAM_PATH);
            if (pathValue.isEmpty()) {
                throw new ConsoleUIException(NO_REQUIRED_PARAMETER,
                        "For file search context need specify -path parameter");
            }

            return SearchContext.ofFile(Paths.get(pathValue.get()));
        }

        throw new ConsoleUIException(NO_REQUIRED_PARAMETER,
                "For search context need specify -console or -file parameter");
    }

    @Nonnull
    @Override
    public ContextType contextType() {
        return ContextType.SEARCH;
    }
}
