package dev.drf.pokedex.common.mark;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Описание классов, реализации которых требуются для работы класса
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ImplementationRequired {
    @Nonnull
    Class<?>[] classes() default {};
}
