package dev.drf.pokedex.ui.console;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public final class MatcherUtils {
    private MatcherUtils() {
    }

    public static <T> Matcher<T> hasId(long id) {
        return hasProperty("id", equalTo(id));
    }

    public static <T> Matcher<T> hasDictionary(String dictionary, long code) {
        return hasProperty(dictionary, hasProperty("code", equalTo(code)));
    }
}
