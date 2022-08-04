package dev.drf.pokedex.ui.console.scenario.step;

import dev.drf.pokedex.model.Pokemon;
import dev.drf.pokedex.ui.console.FileService;
import dev.drf.pokedex.ui.console.JsonConverter;
import dev.drf.pokedex.ui.console.error.ConsoleUIException;
import dev.drf.pokedex.ui.console.error.ErrorCodes;
import dev.drf.pokedex.ui.console.scenario.context.ModifyContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PokemonReadScenarioStepTest {
    @Mock
    private FileService service;
    @Mock
    private JsonConverter<Pokemon> converter;

    @InjectMocks
    private PokemonReadScenarioStep scenarioStep;

    @Test
    void shouldReturnPokemon_whenConverterSuccessfulProcessJson() {
        // arrange
        Mockito.when(service.readFromFile(any())).thenReturn("json");
        Mockito.when(converter.parse(anyString())).thenReturn(new Pokemon());

        // act
        Pokemon pokemon = scenarioStep.process(Paths.get("test", "path"), ModifyContext.ofConsole());

        // assert
        assertNotNull(pokemon);
    }

    @Test
    void shouldThrowException_whenPokemonIsNull() {
        // arrange
        Mockito.when(service.readFromFile(any())).thenReturn("json");
        Mockito.when(converter.parse(anyString())).thenReturn(null);

        // act
        ConsoleUIException error = assertThrows(ConsoleUIException.class, () -> scenarioStep.process(
                Paths.get("test", "path"), ModifyContext.ofConsole()));

        // assert
        assertEquals(ErrorCodes.NULL_PARSE_RESULT, error.getErrorCode());
    }

    @Test
    void shouldCorrectOrder_whenScenarioStepProcessing() {
        // arrange
        Mockito.when(service.readFromFile(any())).thenReturn("json");
        Mockito.when(converter.parse(anyString())).thenReturn(new Pokemon());

        InOrder inOrder = Mockito.inOrder(service, converter);

        // act
        scenarioStep.process(Paths.get("test", "path"), ModifyContext.ofConsole());

        // assert
        inOrder.verify(service).readFromFile(any());
        inOrder.verify(converter).parse(anyString());
        inOrder.verifyNoMoreInteractions();
    }
}
