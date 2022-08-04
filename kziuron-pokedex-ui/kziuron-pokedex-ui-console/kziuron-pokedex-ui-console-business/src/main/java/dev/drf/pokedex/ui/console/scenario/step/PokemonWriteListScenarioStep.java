package dev.drf.pokedex.ui.console.scenario.step;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.ConsoleService;
import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.error.ErrorCodes;
import dev.drf.pokedex.ui.console.scenario.ScenarioResult;
import dev.drf.pokedex.ui.console.scenario.ScenarioStep;
import dev.drf.pokedex.ui.console.scenario.context.DataType;
import dev.drf.pokedex.ui.console.scenario.context.SearchContext;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.List;

/**
 * Запись данных о списке покемонов (в формате JSON)
 * На экран или в файл в зависимости от параметра вывода
 */
public class PokemonWriteListScenarioStep implements ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext> {
    private final FileService service;
    private final ConsoleService consoleService;
    private final JsonConverter<List<Pokemon>> jsonConverter;

    public PokemonWriteListScenarioStep(FileService service,
                                        ConsoleService consoleService,
                                        JsonConverter<List<Pokemon>> jsonConverter) {
        this.service = service;
        this.consoleService = consoleService;
        this.jsonConverter = jsonConverter;
    }

    @Nonnull
    @Override
    public ScenarioResult<List<Pokemon>> process(@Nonnull List<Pokemon> parameter,
                                                 @Nonnull SearchContext context) {
        String json = jsonConverter.toJson(parameter);
        if (json == null) {
            throw new ConsoleUIException(ErrorCodes.NULL_JSON_RESULT);
        }

        DataType dataType = context.dataType();

        switch (dataType) {
            case CONSOLE -> consoleService.write(json);
            case FILE -> {
                Path path = context.path();

                if (path == null) {
                    throw new ConsoleUIException(ErrorCodes.NULL_PATH);
                }
                service.writeToFile(path, json);
            }
            default -> throw new ConsoleUIException(ErrorCodes.UNKNOWN_DATA_TYPE);
        }

        return ScenarioResult.success(parameter);
    }
}
