package dev.drf.pokedex.ui.console.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleServiceImplTest {
    private static final String content = "TEST read content\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ConsoleServiceImpl service;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        service = new ConsoleServiceImpl(new Scanner(content));
    }

    @AfterEach
    public void dropValue() {
        System.setOut(originalOut);
    }

    @Test
    void shouldCorrectWriteLine_whenWriteStringToConsoleService() {
        // arrange
        String value = "TEST: console output";

        // act - assert
        assertDoesNotThrow(() -> service.write(value));

        // assert
        String result = outContent.toString();
        assertEquals("TEST: console output\n", result);
    }

    @Test
    void shouldReadCorrectValue_whenReadStringFromConsoleService() {
        // act
        String value = assertDoesNotThrow(() -> service.read());

        // assert
        assertEquals("TEST read content", value);
    }
}
