package dev.drf.pokedex.ui.console.command;

import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;

import javax.annotation.Nonnull;

public interface ContextTypeDetector {
    @Nonnull
    ContextType detect(@Nonnull Command command);
}
