package dev.drf.pokedex.ui.console.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;

import javax.annotation.Nullable;
import java.util.List;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.JSON_CONVERTER_ERROR;
import static dev.drf.pokedex.ui.console.json.JsonConverterHelper.getMapper;

public class PokemonJsonListConverter implements JsonConverter<List<Pokemon>> {
    private final ObjectMapper mapper;
    private final CollectionType collectionType;

    public PokemonJsonListConverter() {
        this.mapper = getMapper();
        this.collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Pokemon.class);
    }

    @Nullable
    @Override
    public List<Pokemon> parse(@Nullable String json) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, collectionType);
        } catch (JsonProcessingException ex) {
            throw new ConsoleUIException(JSON_CONVERTER_ERROR, "Parse error: " + ex);
        }
    }

    @Nullable
    @Override
    public String toJson(@Nullable List<Pokemon> value) {
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
