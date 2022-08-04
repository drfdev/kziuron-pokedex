package dev.drf.pokedex.ui.console.files;

import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.drf.pokedex.ui.console.error.ErrorCodes.FILE_READ_ERROR;
import static dev.drf.pokedex.ui.console.error.ErrorCodes.FILE_WRITE_ERROR;

public class FileServiceImpl implements FileService {
    @Override
    public void writeToFile(@Nonnull Path path, @Nonnull String data) {
        try {
            Files.writeString(path, data);
        } catch (IOException ex) {
            throw new ConsoleUIException(FILE_WRITE_ERROR, "Write file error, path: " + path, ex);
        }
    }

    @Nonnull
    @Override
    public String readFromFile(@Nonnull Path path) {
        try {
            return Files.readString(path);
        } catch (IOException ex) {
            throw new ConsoleUIException(FILE_READ_ERROR, "Read file error, path: " + path, ex);
        }
    }
}
