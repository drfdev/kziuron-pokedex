package dev.drf.pokedex.ui.console.scenario.command;

import dev.drf.pokedex.api.business.PokemonApiService;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.scenario.ScenarioError;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.INCORRECT_PARAMETERS;
import static dev.drf.pokedex.ui.console.utils.ScenarioResultUtils.toScenarioResult;

public class UpdateScenario extends AbstractScenario<ModifyContext, Pokemon> {
    private final ConsoleService consoleService;
    private final PokemonApiService pokemonApiService;
    private final ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep;

    public UpdateScenario(ConsoleService consoleService,
                          PokemonApiService pokemonApiService,
                          ScenarioStep<Path, Pokemon, ModifyContext> pokemonReadStep) {
        this.consoleService = consoleService;
        this.pokemonApiService = pokemonApiService;
        this.pokemonReadStep = pokemonReadStep;
    }

    @Nonnull
    @Override
    public ScenarioResult<Pokemon> safeExecute(@Nonnull ModifyContext context) {
        consoleService.write("Input file name for read: ");
        String filePath = consoleService.read();

        if (filePath == null) {
            return ScenarioResult.error(ScenarioError.of(INCORRECT_PARAMETERS));
        }

        Path path = Paths.get(filePath);
        Pokemon pokemon = pokemonReadStep.process(path, context);

        ApiResult<Pokemon> result = pokemonApiService.update(pokemon);
        return toScenarioResult(result);
    }

    @Nonnull
    @Override
    public Command key() {
        return Commands.UPDATE;
    }
}
