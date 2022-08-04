package dev.drf.pokedex.ui.console;

import dev.drf.pokedex.model.dictionary.Dictionary;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public final class TestUtils {
    private static final DateTimeFormatter ZONED_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd-MM-yyyy")
            .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
            .toFormatter()
            .withZone(ZoneOffset.UTC);


    private TestUtils() {
    }

    public static Instant toInstant(String value) {
        return ZONED_FORMATTER.parse(value, Instant::from);
    }

    public static <T extends Dictionary> T asDictionary(Class<T> clazz, long code) {
        T dictionary = newDictionary(clazz);
        dictionary.setCode(code);
        return dictionary;
    }

    public static <T extends Dictionary> T asDictionary(Class<T> clazz, long code, String name) {
        T dictionary = asDictionary(clazz, code);
        dictionary.setName(name);
        return dictionary;
    }

    private static <T extends Dictionary> T newDictionary(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new AssertionError(ex);
        }
    }
}
