package dev.drf.pokedex.ui.console.json;

import dev.drf.pokedex.model.Pokemon;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static dev.drf.pokedex.ui.console.MatcherUtils.hasDictionary;
import static dev.drf.pokedex.ui.console.MatcherUtils.hasId;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PokemonJsonListConverterDeserializeTest {
    private final PokemonJsonListConverter converter = new PokemonJsonListConverter();

    @Test
    void shouldCorrectDeserialize_whenSimpleJsonObject() {
        // arrange
        String json = "[]";

        // act
        List<Pokemon> pokemon = converter.parse(json);

        // assert
        assertNotNull(pokemon);
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasSingleId() {
        // arrange
        String json = """
                [
                    {
                        "id":10000
                    },
                    {
                        "id":20000
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("id", equalTo(10_000L))
                ),
                allOf(
                        hasProperty("id", equalTo(20_000L))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasBusinessEntityField() {
        // arrange
        String json = """
                [
                    {
                        "id":10000,
                        "version":2,
                        "versionDate":"2002-02-22T00:00:00Z",
                        "endDate":"2012-03-12T00:00:00Z"
                    },
                    {
                        "id":20000,
                        "version":1,
                        "versionDate":"2003-02-22T00:00:00Z",
                        "endDate":"2015-03-12T00:00:00Z"
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("id", equalTo(10_000L)),
                        hasProperty("version", equalTo(2)),
                        hasProperty("versionDate", equalTo(toInstant("22-02-2002"))),
                        hasProperty("endDate", equalTo(toInstant("12-03-2012")))
                ),
                allOf(
                        hasProperty("id", equalTo(20_000L)),
                        hasProperty("version", equalTo(1)),
                        hasProperty("versionDate", equalTo(toInstant("22-02-2003"))),
                        hasProperty("endDate", equalTo(toInstant("12-03-2015")))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasType() {
        // arrange
        String json = """
                [
                    {
                        "pokemonType":{
                            "code":2,
                            "name":"test"
                        }
                    },
                    {
                        "pokemonType":{
                            "code":5,
                            "name":"stub"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("pokemonType", allOf(
                                hasProperty("code", equalTo(2L)),
                                hasProperty("name", equalTo("test"))
                        ))
                ),
                allOf(
                        hasProperty("pokemonType", allOf(
                                hasProperty("code", equalTo(5L)),
                                hasProperty("name", equalTo("stub"))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasElementType() {
        // arrange
        String json = """
                [
                    {
                        "elementType":{
                            "code":3,
                            "name":"test"
                        }
                    },
                    {
                        "elementType":{
                            "code":5,
                            "name":"stub"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("elementType", allOf(
                                hasProperty("code", equalTo(3L)),
                                hasProperty("name", equalTo("test"))
                        ))
                ),
                allOf(
                        hasProperty("elementType", allOf(
                                hasProperty("code", equalTo(5L)),
                                hasProperty("name", equalTo("stub"))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasStatus() {
        // arrange
        String json = """
                [
                    {
                        "status":{
                            "code":1,
                            "name":"test-1"
                        }
                    },
                    {
                        "status":{
                            "code":2,
                            "name":"test-2"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("status", allOf(
                                hasProperty("code", equalTo(1L)),
                                hasProperty("name", equalTo("test-1"))
                        ))
                ),
                allOf(
                        hasProperty("status", allOf(
                                hasProperty("code", equalTo(2L)),
                                hasProperty("name", equalTo("test-2"))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCatchPlace() {
        // arrange
        String json = """
                [
                    {
                        "catchPlace":{
                            "id":12500,
                            "placeName":"test-place-name",
                            "city":"test-city",
                            "region":{
                                "code":12
                            }
                        }
                    },
                    {
                        "catchPlace":{
                            "id":17700,
                            "placeName":"test-stub",
                            "city":"test-town",
                            "region":{
                                "code":13
                            }
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("catchPlace", allOf(
                                hasProperty("id", equalTo(12_500L)),
                                hasProperty("placeName", equalTo("test-place-name")),
                                hasProperty("city", equalTo("test-city")),
                                hasProperty("region", hasProperty("code", equalTo(12L)))
                        ))
                ),
                allOf(
                        hasProperty("catchPlace", allOf(
                                hasProperty("id", equalTo(17_700L)),
                                hasProperty("placeName", equalTo("test-stub")),
                                hasProperty("city", equalTo("test-town")),
                                hasProperty("region", hasProperty("code", equalTo(13L)))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCatchDate() {
        // arrange
        String json = """
                [
                    {
                        "catchDate":{
                            "id":12000,
                            "version":1,
                            "catchDate":"1990-05-19"
                        }
                    },
                    {
                        "catchDate":{
                            "id":19000,
                            "version":2,
                            "catchDate":"1995-05-19"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("catchDate", allOf(
                                hasProperty("id", equalTo(12_000L)),
                                hasProperty("version", equalTo(1)),
                                hasProperty("catchDate", equalTo(LocalDate.of(1990, 5, 19)))
                        ))
                ),
                allOf(
                        hasProperty("catchDate", allOf(
                                hasProperty("id", equalTo(19_000L)),
                                hasProperty("version", equalTo(2)),
                                hasProperty("catchDate", equalTo(LocalDate.of(1995, 5, 19)))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasPokemonName() {
        // arrange
        String json = """
                [
                    {
                        "pokemonName":{
                            "id":13000,
                            "nickname":"nick-name-value",
                            "name":"name-value",
                            "title":"title-value"
                        }
                    },
                    {
                        "pokemonName":{
                            "id":14500,
                            "nickname":"test-name",
                            "name":"test-value",
                            "title":"title-test"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("pokemonName", allOf(
                                hasProperty("id", equalTo(13_000L)),
                                hasProperty("nickname", equalTo("nick-name-value")),
                                hasProperty("name", equalTo("name-value")),
                                hasProperty("title", equalTo("title-value"))
                        ))
                ),
                allOf(
                        hasProperty("pokemonName", allOf(
                                hasProperty("id", equalTo(14_500L)),
                                hasProperty("nickname", equalTo("test-name")),
                                hasProperty("name", equalTo("test-value")),
                                hasProperty("title", equalTo("title-test"))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCombatStats() {
        // arrange
        String json = """
                [
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
                    },
                    {
                        "combatStats":{
                            "id":12000,
                            "hp":100,
                            "attack":13,
                            "defence":125,
                            "specialAttack":37,
                            "specialDefence":89,
                            "speed":22,
                            "evasion":43,
                            "accuracy":9
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("combatStats", allOf(
                                hasProperty("id", equalTo(11_000L)),
                                hasProperty("hp", equalTo(200)),
                                hasProperty("attack", equalTo(12)),
                                hasProperty("defence", equalTo(225)),
                                hasProperty("specialAttack", equalTo(18)),
                                hasProperty("specialDefence", equalTo(173)),
                                hasProperty("speed", equalTo(33)),
                                hasProperty("evasion", equalTo(52)),
                                hasProperty("accuracy", equalTo(14))
                        ))
                ),
                allOf(
                        hasProperty("combatStats", allOf(
                                hasProperty("id", equalTo(12_000L)),
                                hasProperty("hp", equalTo(100)),
                                hasProperty("attack", equalTo(13)),
                                hasProperty("defence", equalTo(125)),
                                hasProperty("specialAttack", equalTo(37)),
                                hasProperty("specialDefence", equalTo(89)),
                                hasProperty("speed", equalTo(22)),
                                hasProperty("evasion", equalTo(43)),
                                hasProperty("accuracy", equalTo(9))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasActiveSkills() {
        // arrange
        String json = """
                [
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
                    },
                    {
                    "activeSkills":[
                        {
                            "id":30000,
                            "name":"skill-3",
                            "skillType":{
                                "code":3
                            },
                            "elementType":{
                                "code":7
                            },
                            "minValue":8,
                            "maxValue":18
                        }
                    ]
                }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("activeSkills", containsInAnyOrder(
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
                        ))
                ),
                allOf(
                        hasProperty("activeSkills", containsInAnyOrder(
                                allOf(
                                        hasId(30_000L),
                                        hasProperty("name", equalTo("skill-3")),
                                        hasDictionary("skillType", 3L),
                                        hasDictionary("elementType", 7L),
                                        hasProperty("minValue", equalTo(8)),
                                        hasProperty("maxValue", equalTo(18))
                                )
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasPassiveSkills() {
        // arrange
        String json = """
                [
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
                    },
                    {
                        "passiveSkills":[
                            {
                                "id":12000,
                                "name":"name-3",
                                "bonusType":{
                                    "code":7
                                },
                                "bonusValue":9
                            }
                        ]
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);

        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("passiveSkills", containsInAnyOrder(
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
                        ))
                ),
                allOf(
                        hasProperty("passiveSkills", containsInAnyOrder(
                                allOf(
                                        hasId(12_000L),
                                        hasProperty("name", equalTo("name-3")),
                                        hasDictionary("bonusType", 7L),
                                        hasProperty("bonusValue", equalTo(9))
                                )
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectDeserialize_whenPokemonHasCharacterStructure() {
        // arrange
        String json = """
                [
                    {
                        "characterStructure":{
                            "code":15,
                            "name":"test-value"
                        }
                    },
                    {
                        "characterStructure":{
                            "code":17,
                            "name":"test-stub"
                        }
                    }
                ]
                """;

        // act
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("characterStructure", allOf(
                                hasProperty("code", equalTo(15L)),
                                hasProperty("name", equalTo("test-value"))
                        ))
                ),
                allOf(
                        hasProperty("characterStructure", allOf(
                                hasProperty("code", equalTo(17L)),
                                hasProperty("name", equalTo("test-stub"))
                        ))
                )
        ));
    }
}
