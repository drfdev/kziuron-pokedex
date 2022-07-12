package dev.drf.pokedex.ui.console.scenario.context;

import dev.drf.pokedex.ui.console.scenario.ScenarioContext;

public final class AuthorizationContext implements ScenarioContext {
    @Override
    public ContextType contextType() {
        return ContextType.AUTHORIZATION;
    }
}
