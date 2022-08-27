package dev.drf.pokedex.ui.console.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonConverterHelper {
    private static final ObjectMapper OBJECT_MAPPER = configureMapper();
    private JsonConverterHelper() {
    }

    static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    private static ObjectMapper configureMapper() {
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
