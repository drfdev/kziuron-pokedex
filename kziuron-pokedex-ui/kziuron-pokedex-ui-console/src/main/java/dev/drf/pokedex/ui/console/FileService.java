package dev.drf.pokedex.ui.console;

import javax.annotation.Nonnull;
import java.nio.file.Path;

public interface FileService {
    void writeToFile(@Nonnull Path path,
                     @Nonnull String data);

    @Nonnull
    String readFromFile(@Nonnull Path path);
}
