package dev.drf.pokedex.ui.console.json;

import dev.drf.pokedex.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.drf.pokedex.ui.console.MatcherUtils.hasDictionary;
import static dev.drf.pokedex.ui.console.MatcherUtils.hasId;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PokemonJsonConverterDeserializeTest {
    private final PokemonJsonConverter converter = new PokemonJsonConverter();

    @Test
    void shouldCorrectDeserialize_whenSimpleJsonObject() {
        // arrange
        String json = "{}";

        // act
        Pokemon pokemon = converter.parse(json);

        // assert
        assertNotNull(pokemon);
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasSingleId() {
        // arrange
        String json = """
                {
                    "id":10000
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertEquals(10_000L, result.getId());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasBusinessEntityField() {
        // arrange
        String json = """
                {
                    "id":10000,
                    "version":2,
                    "versionDate":"2002-02-22T00:00:00Z",
                    "endDate":"2012-03-12T00:00:00Z"
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertEquals(10_000L, result.getId());
        assertEquals(2, result.getVersion());
        assertEquals(toInstant("22-02-2002"), result.getVersionDate());
        assertEquals(toInstant("12-03-2012"), result.getEndDate());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasType() {
        // arrange
        String json = """
                {
                    "pokemonType":{
                        "code":2,
                        "name":"test"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getPokemonType());
        assertEquals(2L, result.getPokemonType().getCode());
        assertEquals("test", result.getPokemonType().getName());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasElementType() {
        // arrange
        String json = """
                {
                    "elementType":{
                        "code":3,
                        "name":"test"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getElementType());
        assertEquals(3L, result.getElementType().getCode());
        assertEquals("test", result.getElementType().getName());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasStatus() {
        // arrange
        String json = """
                {
                    "status":{
                        "code":1,
                        "name":"test-1"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getStatus());
        assertEquals(1L, result.getStatus().getCode());
        assertEquals("test-1", result.getStatus().getName());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCatchPlace() {
        // arrange
        String json = """
                {
                    "catchPlace":{
                        "id":12500,
                        "placeName":"test-place-name",
                        "city":"test-city",
                        "region":{
                            "code":12
                        }
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getCatchPlace());

        CatchPlace resultPlace = result.getCatchPlace();
        assertEquals(12_500L, resultPlace.getId());
        assertEquals("test-place-name", resultPlace.getPlaceName());
        assertEquals("test-city", resultPlace.getCity());
        assertNotNull(resultPlace.getRegion());
        assertEquals(12L, resultPlace.getRegion().getCode());
        assertNull(resultPlace.getRegion().getName());

        assertNull(resultPlace.getVersion());
        assertNull(resultPlace.getVersionDate());
        assertNull(resultPlace.getEndDate());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCatchDate() {
        // arrange
        String json = """
                {
                    "catchDate":{
                        "id":12000,
                        "version":1,
                        "catchDate":"1990-05-19"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getCatchDate());

        CatchDate resultDate = result.getCatchDate();
        assertEquals(12_000L, resultDate.getId());
        assertEquals(1, resultDate.getVersion());
        assertEquals(LocalDate.of(1990, 5, 19), resultDate.getCatchDate());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasPokemonName() {
        // arrange
        String json = """
                {
                    "pokemonName":{
                        "id":13000,
                        "nickname":"nick-name-value",
                        "name":"name-value",
                        "title":"title-value"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getPokemonName());

        PokemonName resultName = result.getPokemonName();
        assertEquals(13_000L, resultName.getId());
        assertEquals("nick-name-value", resultName.getNickname());
        assertEquals("name-value", resultName.getName());
        assertEquals("title-value", resultName.getTitle());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCombatStats() {
        // arrange
        String json = """
                {
                    "combatStats":{
                        "id":11000,
                        "hp":200,
                        "attack":12,
                        "defence":225,
                        "specialAttack":18,
                        "specialDefence":173,
                        "speed":33,
                        "evasion":52,
                        "accuracy":14
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getCombatStats());

        CombatStats resultStat = result.getCombatStats();
        assertEquals(11_000L, resultStat.getId());
        assertEquals(200, resultStat.getHp());
        assertEquals(12, resultStat.getAttack());
        assertEquals(225, resultStat.getDefence());
        assertEquals(18, resultStat.getSpecialAttack());
        assertEquals(173, resultStat.getSpecialDefence());
        assertEquals(33, resultStat.getSpeed());
        assertEquals(52, resultStat.getEvasion());
        assertEquals(14, resultStat.getAccuracy());
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasActiveSkills() {
        // arrange
        String json = """
                {
                    "activeSkills":[
                        {
                            "id":10000,
                            "name":"skill-1",
                            "skillType":{
                                "code":1
                            },
                            "elementType":{
                                "code":2
                            },
                            "minValue":10,
                            "maxValue":22
                        },
                        {
                            "id":11000,
                            "name":"skill-2",
                            "skillType":{
                                "code":3
                            },
                            "elementType":{
                                "code":4
                            },
                            "minValue":8,
                            "maxValue":25
                        }
                    ]
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getActiveSkills());

        assertThat(result.getActiveSkills(), containsInAnyOrder(
                allOf(
                        hasId(10_000L),
                        hasProperty("name", equalTo("skill-1")),
                        hasDictionary("skillType", 1L),
                        hasDictionary("elementType", 2L),
                        hasProperty("minValue", equalTo(10)),
                        hasProperty("maxValue", equalTo(22))
                ),
                allOf(
                        hasId(11_000L),
                        hasProperty("name", equalTo("skill-2")),
                        hasDictionary("skillType", 3L),
                        hasDictionary("elementType", 4L),
                        hasProperty("minValue", equalTo(8)),
                        hasProperty("maxValue", equalTo(25))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasPassiveSkills() {
        // arrange
        String json = """
                {
                    "passiveSkills":[
                        {
                            "id":7000,
                            "name":"name-1",
                            "bonusType":{
                                "code":22
                            },
                            "bonusValue":120
                        },
                        {
                            "id":9000,
                            "name":"name-2",
                            "bonusType":{
                                "code":27
                            },
                            "bonusValue":130
                        }
                    ]
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getPassiveSkills());

        assertThat(result.getPassiveSkills(), containsInAnyOrder(
                allOf(
                        hasId(7_000L),
                        hasProperty("name", equalTo("name-1")),
                        hasDictionary("bonusType", 22L),
                        hasProperty("bonusValue", equalTo(120))
                ),
                allOf(
                        hasId(9_000L),
                        hasProperty("name", equalTo("name-2")),
                        hasDictionary("bonusType", 27L),
                        hasProperty("bonusValue", equalTo(130))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCharacterStructure() {
        // arrange
        String json = """
                {
                    "characterStructure":{
                        "code":15,
                        "name":"test-value"
                    }
                }
                """;

        // act
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getCharacterStructure());
        assertEquals(15L, result.getCharacterStructure().getCode());
        assertEquals("test-value", result.getCharacterStructure().getName());
    }
}
