package dev.drf.pokedex.ui.console.command;

import dev.drf.pokedex.ui.console.Command;

import javax.annotation.Nonnull;

public enum Commands implements Command {
    /**
     * авторизация
     */
    AUTHORIZATION("authorization"),
    /**
     * получить данные по ID (вывод в файл или на экран)
     */
    GET("get"),
    /**
     * создать покемона (из файла в формате JSON)
     */
    CREATE("create"),
    /**
     * обновить покемона (из файла в формате JSON)
     */
    UPDATE("update"),
    /**
     * деактивировать покемона (из файла в формате JSON)
     */
    DEACTIVATE("deactivate"),
    /**
     * вызов smart-update (из файла в формате JSON)
     */
    SMART_UPDATE("smart-update"),
    /**
     * поиск по ID и версии (вывод в файл или на экран)
     */
    SEARCH_BY_VERSION("search-version"),
    /**
     * поиск по покемона по имени (вывод в файл или на экран)
     */
    SEARCH_BY_NAME("search-name"),
    ;

    private final String key;

    Commands(String key) {
        this.key = key;
    }

    @Nonnull
    @Override
    public String key() {
        return key;
    }
}
