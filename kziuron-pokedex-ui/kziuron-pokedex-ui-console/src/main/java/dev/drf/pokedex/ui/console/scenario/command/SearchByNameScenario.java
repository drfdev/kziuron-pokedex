package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.SearchPokemonApiService;
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
import java.util.List;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INCORRECT_PARAMETERS;
import static dev.drf.pokedex.ui.console.utils.ScenarioResultUtils.toScenarioResult;

public class SearchByNameScenario extends AbstractScenario<SearchContext, List<Pokemon>> {
    private final ConsoleService consoleService;
    private final SearchPokemonApiService searchPokemonApiService;
    private final ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> pokemonWriteStep;

    public SearchByNameScenario(ConsoleService consoleService,
                                SearchPokemonApiService searchPokemonApiService,
                                ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> pokemonWriteStep) {
        this.consoleService = consoleService;
        this.searchPokemonApiService = searchPokemonApiService;
        this.pokemonWriteStep = pokemonWriteStep;
    }

    @Nonnull
    @Override
    protected ScenarioResult<List<Pokemon>> safeExecute(@Nonnull SearchContext context) {
        consoleService.write("Input pokemon name: ");
        String name = consoleService.read();

        consoleService.write("Input pokemon title: ");
        String title = consoleService.read();

        consoleService.write("Input pokemon nickname: ");
        String nickname = consoleService.read();

        if (name == null && title == null && nickname == null) {
            return ScenarioResult.error(ScenarioError.of(INCORRECT_PARAMETERS));
        }

        ApiResult<List<Pokemon>> pokemonResult = searchPokemonApiService.searchByPokemonName(name, title, nickname);

        ScenarioResult<List<Pokemon>> result = toScenarioResult(pokemonResult);
        if (result.status() == ScenarioStatus.ERROR
                || result.payload() == null) {
            return result;
        }

        return pokemonWriteStep.process(result.payload(), context);
    }

    @Nonnull
    @Override
    public Command key() {
        return Commands.SEARCH_BY_NAME;
    }
}
