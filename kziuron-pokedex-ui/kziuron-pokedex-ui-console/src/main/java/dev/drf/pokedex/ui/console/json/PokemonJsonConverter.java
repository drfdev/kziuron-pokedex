package dev.drf.pokedex.ui.console.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;

import javax.annotation.Nullable;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.JSON_CONVERTER_ERROR;

/**
 * JSON-конвертер для покемонов
 */
public class PokemonJsonConverter implements JsonConverter<Pokemon> {
    private final ObjectMapper mapper;

    public PokemonJsonConverter() {
        this.mapper = configureMapper();
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

    private ObjectMapper configureMapper() {
        // Корректная сериализация дат
        JavaTimeModule timeModule = new JavaTimeModule();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(timeModule);

        // Дополнительные параметры конфигурации (сериализации)
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        // десериализации
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        // Игнорировать null-значения
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }
}
