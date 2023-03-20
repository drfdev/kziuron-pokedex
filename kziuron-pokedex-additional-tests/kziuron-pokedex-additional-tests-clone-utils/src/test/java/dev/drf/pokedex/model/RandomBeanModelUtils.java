package dev.drf.pokedex.model;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomBeanModelUtils {
    private RandomBeanModelUtils() {
    }

    @Nonnull
    public static EnhancedRandom buildEnhancedRandom() {
        return EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .seed(ThreadLocalRandom.current().nextLong())
                .objectPoolSize(100)
                .randomizationDepth(7)
                .charset(StandardCharsets.UTF_8)
                .timeRange(LocalTime.of(5, 0), LocalTime.of(22, 0))
                .dateRange(LocalDate.of(1980, Month.JANUARY, 1), LocalDate.of(2022, Month.JANUARY, 1))
                .stringLengthRange(5, 50)
                .collectionSizeRange(3, 5)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .build();
    }
}
