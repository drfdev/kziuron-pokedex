package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.ui.console.Scenario;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;

import javax.annotation.Nonnull;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INNER_ERROR;

abstract class AbstractScenario<C extends ScenarioContext, R> implements Scenario<C, R> {

    protected abstract ScenarioResult<R> safeExecute(@Nonnull C context);

    @Nonnull
    @Override
    public ScenarioResult<R> execute(@Nonnull C context) {
        try {
            return safeExecute(context);
        } catch (ConsoleUIException uiEx) {
            return ScenarioResult.error(ScenarioError.of(uiEx.getErrorCode(), "UI Console Error: " + uiEx.getMessage()));
        } catch (Exception ex) {
            return ScenarioResult.error(ScenarioError.of(INNER_ERROR, "Unexpected error: " + ex.getMessage()));
        }
    }
}
