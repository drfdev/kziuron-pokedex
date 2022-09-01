package dev.drf.pokedex.ui.console.scenario.step;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.error.ErrorCodes;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;

import javax.annotation.Nonnull;
import java.nio.file.Path;

/**
 * Загрузка данных из файла, в формате JSON
 */
public class PokemonReadScenarioStep implements ScenarioStep<Path, Pokemon, ModifyContext> {
    private static final System.Logger LOGGER = System.getLogger(PokemonReadScenarioStep.class.getName());

    private final FileService service;
    private final JsonConverter<Pokemon> converter;

    public PokemonReadScenarioStep(FileService service,
                                   JsonConverter<Pokemon> converter) {
        this.service = service;
        this.converter = converter;
    }

    @Nonnull
    @Override
    public Pokemon process(@Nonnull Path parameter,
                           @Nonnull ModifyContext context) {
        String fileData = service.readFromFile(parameter);
        Pokemon pokemon = converter.parse(fileData);

        if (pokemon == null) {
            LOGGER.log(System.Logger.Level.ERROR, "Parse error: pokemon is null");
            throw new ConsoleUIException(ErrorCodes.NULL_PARSE_RESULT);
        }

        return pokemon;
    }
}
