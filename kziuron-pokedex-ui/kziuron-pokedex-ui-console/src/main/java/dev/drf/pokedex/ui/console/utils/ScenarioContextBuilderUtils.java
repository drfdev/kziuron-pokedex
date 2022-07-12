package dev.drf.pokedex.ui.console.utils;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

public final class ScenarioContextBuilderUtils {
    private static final int UNKNOWN_INDEX = -1;
    private static final String EQUAL_COMA = "=";

    public static final String PARAM_CONSOLE = "-console";
    public static final String PARAM_FILE = "-file";
    public static final String PARAM_PATH = "-path";

    private ScenarioContextBuilderUtils() {
    }

    public static boolean hasParameter(@Nonnull String[] lineParams,
                                       @Nonnull String paramName) {
        if (lineParams.length == 0) {
            return false;
        }

        final int index = searchParamIndex(lineParams, paramName);
        return index != UNKNOWN_INDEX;
    }

    public static Optional<String> getParameterValue(@Nonnull String[] lineParams,
                                                     @Nonnull String paramName) {
        if (lineParams.length == 0) {
            return Optional.empty();
        }

        final int index = searchParamIndex(lineParams, paramName);
        if (index == UNKNOWN_INDEX) {
            return Optional.empty();
        }

        String lineParam = lineParams[index];
        if (lineParam.contains(EQUAL_COMA)) {
            final int comaIndex = lineParam.indexOf(EQUAL_COMA) + 1;
            return Optional.of(lineParam.substring(comaIndex));
        }

        if (index < lineParams.length - 1) {
            return Optional.of(lineParams[index + 1]);
        }

        return Optional.empty();
    }

    private static int searchParamIndex(@Nonnull String[] lineParams,
                                        @Nonnull String paramName) {
        int index = UNKNOWN_INDEX;
        for (int i = 0; i < lineParams.length; i++) {
            String lineParam = lineParams[i];
            if (StringUtils.startsWithIgnoreCase(lineParam, paramName)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
