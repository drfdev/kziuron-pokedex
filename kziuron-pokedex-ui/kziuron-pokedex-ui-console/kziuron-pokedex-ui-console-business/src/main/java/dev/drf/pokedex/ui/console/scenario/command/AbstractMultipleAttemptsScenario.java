package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.context.MultipleAttemptsContext;

import javax.annotation.Nonnull;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.NULL_RESULT;

abstract class AbstractMultipleAttemptsScenario<C extends ScenarioContext, R> extends AbstractScenario<C, R> {

    protected abstract ScenarioResult<R> singleAttempt(@Nonnull C context,
                                                       int attempt);

    @Nonnull
    protected ScenarioResult<R> multipleExecute(@Nonnull MultipleAttemptsContext<C> parameters) {
        ScenarioResult<R> currentResult = null;

        while (parameters.hasMoreAttempts()) {
            final int attempt = parameters.incrementAttempt();

            currentResult = singleAttempt(parameters.getContext(), attempt);
            if (currentResult.status() == ScenarioStatus.SUCCESS) {
                break;
            }
        }

        if (currentResult == null) {
            currentResult = ScenarioResult.error(ScenarioError.of(NULL_RESULT));
        }

        return currentResult;
    }


}
