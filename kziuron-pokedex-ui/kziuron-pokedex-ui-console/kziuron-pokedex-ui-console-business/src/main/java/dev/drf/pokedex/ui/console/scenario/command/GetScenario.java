package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStatus;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;

import javax.annotation.Nonnull;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INCORRECT_PARAMETERS;
import static dev.drf.pokedex.ui.console.utils.ScenarioResultUtils.toScenarioResult;

public class GetScenario extends AbstractScenario<SearchContext, Pokemon> {
    private final ConsoleService consoleService;
    private final PokemonApiService pokemonApiService;
    private final ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep;

    public GetScenario(ConsoleService consoleService,
                       PokemonApiService pokemonApiService,
                       ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext> pokemonWriteStep) {
        this.consoleService = consoleService;
        this.pokemonApiService = pokemonApiService;
        this.pokemonWriteStep = pokemonWriteStep;
    }

    @Nonnull
    @Override
    protected ScenarioResult<Pokemon> safeExecute(@Nonnull SearchContext context) {
        consoleService.write("Input pokemon id: ");
        String pokemonIdFromConsole = consoleService.read();

        if (pokemonIdFromConsole == null) {
            return ScenarioResult.error(ScenarioError.of(INCORRECT_PARAMETERS));
        }

        long pokemonId = Long.parseLong(pokemonIdFromConsole);
        ApiResult<Pokemon> pokemonResult = pokemonApiService.get(pokemonId);

        ScenarioResult<Pokemon> result = toScenarioResult(pokemonResult);
        if (result.status() == ScenarioStatus.ERROR
                || result.payload() == null) {
            return result;
        }

        return pokemonWriteStep.process(result.payload(), context);
    }

    @Nonnull
    @Override
    public Command key() {
        return Commands.GET;
    }
}
