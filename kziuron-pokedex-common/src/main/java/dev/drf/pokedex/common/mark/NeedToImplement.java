package dev.drf.pokedex.common.mark;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Описание классов, которые нужно реализовать перед использованием данного класса (или конфигурации)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface NeedToImplement {
    @Nonnull
    Class<?>[] classes() default {};
}
