package dev.drf.pokedex.ui.console.scenario.context;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public final class MultipleAttemptsContext<C extends ScenarioContext> implements ScenarioContext {
    /**
     * Контекст операции
     */
    private final C context;
    /**
     * Максимальное количество попыток
     */
    private final int attempts;
    /**
     * Текущая попытка
     */
    private int currentAttempt = 0;

    private MultipleAttemptsContext(@Nonnull C context,
                                    int attempts) {
        this.context = requireNonNull(context);
        this.attempts = attempts;
    }

    public static <P extends ScenarioContext> MultipleAttemptsContext<P> of(@Nonnull P context,
                                                                            int attempts) {
        return new MultipleAttemptsContext<>(context, attempts);
    }

    public C getContext() {
        return context;
    }

    public int getAttempts() {
        return attempts;
    }

    public int incrementAttempt() {
        return this.currentAttempt ++;
    }

    public boolean hasMoreAttempts() {
        return this.currentAttempt < this.attempts;
    }

    @Override
    public String toString() {
        return "MultipleAttemptsContext{" +
                "context=" + context +
                ", attempts=" + attempts +
                ", currentAttempt=" + currentAttempt +
                '}';
    }

    @Override
    public ContextType contextType() {
        return context.contextType();
    }
}
