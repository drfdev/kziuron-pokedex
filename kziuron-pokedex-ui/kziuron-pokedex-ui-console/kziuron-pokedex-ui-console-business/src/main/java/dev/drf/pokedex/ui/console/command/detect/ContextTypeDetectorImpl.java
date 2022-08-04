package dev.drf.pokedex.ui.console.command.detect;

import com.google.common.collect.ImmutableMap;
import dev.drf.pokedex.ui.console.Command;
import dev.drf.pokedex.ui.console.command.Commands;
import dev.drf.pokedex.ui.console.command.ContextTypeDetector;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.scenario.context.ContextType;

import javax.annotation.Nonnull;
import java.util.Map;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.UNKNOWN_CONTEXT_TYPE;

public class ContextTypeDetectorImpl implements ContextTypeDetector {
    private final Map<Command, ContextType> contextTypes;

    public ContextTypeDetectorImpl() {
        this.contextTypes = ImmutableMap.of(
                Commands.AUTHORIZATION, ContextType.AUTHORIZATION,
                Commands.GET, ContextType.SEARCH,
                Commands.CREATE, ContextType.MODIFY,
                Commands.UPDATE, ContextType.MODIFY,
                Commands.DEACTIVATE, ContextType.MODIFY,
                Commands.SMART_UPDATE, ContextType.MODIFY,
                Commands.SEARCH_BY_NAME, ContextType.SEARCH,
                Commands.SEARCH_BY_VERSION, ContextType.SEARCH);
    }

    @Nonnull
    @Override
    public ContextType detect(@Nonnull Command command) {
        ContextType contextType = contextTypes.get(command);
        if (contextType == null) {
            throw new ConsoleUIException(UNKNOWN_CONTEXT_TYPE, "Unknown context type by command: " + command);
        }
        return contextType;
    }
}
