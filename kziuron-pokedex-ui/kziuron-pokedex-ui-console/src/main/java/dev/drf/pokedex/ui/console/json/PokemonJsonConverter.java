package dev.drf.pokedex.ui.console.json;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.JsonConverter;

import javax.annotation.Nullable;

/**
 * JSON-конвертер для покемонов
 * TODO: пока не реализовано
 */
public class PokemonJsonConverter implements JsonConverter<Pokemon> {
    @Nullable
    @Override
    public Pokemon parse(@Nullable String json) {
        // TODO
        return null;
    }

    @Nullable
    @Override
    public String toJson(@Nullable Pokemon value) {
        // TODO
        return null;
    }
}
