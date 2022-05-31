package dev.drf.pokedex.ui.console.context;

import dev.drf.pokedex.ui.console.ConsoleService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Scanner;

/**
 * Реализация контекста консоли
 */
public class ConsoleServiceImpl implements ConsoleService {
    private final Scanner scanner;

    public ConsoleServiceImpl(@Nonnull Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void write(@Nonnull String text) {
        System.out.println(text);
    }

    @Nullable
    @Override
    public String read() {
        return scanner.nextLine();
    }
}
