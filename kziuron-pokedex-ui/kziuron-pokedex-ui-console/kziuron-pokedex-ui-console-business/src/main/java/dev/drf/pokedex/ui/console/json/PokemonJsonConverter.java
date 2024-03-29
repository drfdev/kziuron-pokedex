package dev.drf.pokedex.ui.console.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;

import javax.annotation.Nullable;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.JSON_CONVERTER_ERROR;
import static dev.drf.pokedex.ui.console.json.JsonConverterHelper.getMapper;

/**
 * JSON-конвертер для покемонов
 */
public class PokemonJsonConverter implements JsonConverter<Pokemon> {
    private final ObjectMapper mapper;

    public PokemonJsonConverter() {
        this.mapper = getMapper();
    }

    @Nullable
    @Override
    public Pokemon parse(@Nullable String json) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, Pokemon.class);
        } catch (JsonProcessingException ex) {
            throw new ConsoleUIException(JSON_CONVERTER_ERROR, "Parse error: " + ex);
        }
    }

    @Nullable
    @Override
    public String toJson(@Nullable Pokemon value) {
        if (value == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new ConsoleUIException(JSON_CONVERTER_ERROR, "Json convert error: " + ex);
        }
    }
}
