package dev.drf.pokedex.ui.console.files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileServiceImplTest {
    private final FileServiceImpl service = new FileServiceImpl();

    @Test
    void shouldCorrectWriteIntoFile_whenFileExists() throws IOException {
        // arrange
        File tempFile = File.createTempFile("write-test-", "correct");
        tempFile.deleteOnExit();

        String data = "{\"value\":\"text\"}";
        Path filePath = Paths.get(tempFile.getAbsolutePath());

        // act
        service.writeToFile(filePath, data);

        // assert
        String result = Files.readString(filePath);
        assertEquals(data, result);
    }

    @Test
    void shouldCorrectReadFromFile_whenFileExists() throws IOException {
        // arrange
        File tempFile = File.createTempFile("read-test-", "correct");
        tempFile.deleteOnExit();

        String data = "{\"value\":\"for read\"}";
        Path filePath = Paths.get(tempFile.getAbsolutePath());
        Files.writeString(filePath, data);

        // act
        String result = service.readFromFile(filePath);

        // assert
        assertEquals(data, result);
    }
}
