package dev.drf.pokedex.ui.console.command.detect;

import dev.drf.pokedex.ui.console.command.ScenarioContextBuilder;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;

import javax.annotation.Nonnull;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NO_REQUIRED_PARAMETER;
import static dev.drf.pokedex.ui.console.utils.ScenarioContextBuilderUtils.*;

public class ModifyScenarioContextBuilder implements ScenarioContextBuilder {
    @Nonnull
    @Override
    public ScenarioContext build(@Nonnull String[] params) {
        boolean isConsole = hasParameter(params, PARAM_CONSOLE);
        boolean isFile = hasParameter(params, PARAM_FILE);

        if (isConsole) {
            return ModifyContext.ofConsole();
        } else if (isFile) {
            return ModifyContext.ofFile();
        }

        throw new ConsoleUIException(NO_REQUIRED_PARAMETER,
                "For modify context need specify -console or -file parameter");
    }

    @Nonnull
    @Override
    public ContextType contextType() {
        return ContextType.MODIFY;
    }
}
