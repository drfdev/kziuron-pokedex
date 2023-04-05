package dev.drf.pokedex.ui.console.app.config;

import java.util.List;

public class ListAuthorizationRawConfig {
    private boolean ignoreCase = false;
    private List<ListUser> users;

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public List<ListUser> getUsers() {
        return users;
    }

    public void setUsers(List<ListUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ListAuthorizationRawConfig{" +
                "ignoreCase=" + ignoreCase +
                ", users=" + users +
                '}';
    }
}
