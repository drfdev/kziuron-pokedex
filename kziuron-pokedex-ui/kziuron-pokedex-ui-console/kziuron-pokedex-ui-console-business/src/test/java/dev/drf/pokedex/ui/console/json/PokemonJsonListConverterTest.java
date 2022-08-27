package dev.drf.pokedex.ui.console.json;

import com.google.common.collect.Lists;
import dev.drf.pokedex.model.*;
import dev.drf.pokedex.model.dictionary.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static dev.drf.pokedex.ui.console.MatcherUtils.hasDictionary;
import static dev.drf.pokedex.ui.console.MatcherUtils.hasId;
import static dev.drf.pokedex.ui.console.TestUtils.asDictionary;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PokemonJsonListConverterTest {
    private final PokemonJsonListConverter converter = new PokemonJsonListConverter();

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasSingleId() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(10_000L);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(12_000L);

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasId(10_000L)
                ),
                allOf(
                        hasId(12_000L)
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasBusinessEntityField() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(10_000L);
        pokemon1.setVersion(2);
        pokemon1.setVersionDate(toInstant("22-02-2002"));
        pokemon1.setEndDate(toInstant("12-03-2012"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(12_000L);
        pokemon2.setVersion(3);
        pokemon2.setVersionDate(toInstant("22-02-2007"));
        pokemon2.setEndDate(toInstant("12-03-2014"));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasId(10_000L),
                        hasProperty("version", equalTo(2)),
                        hasProperty("versionDate", equalTo(toInstant("22-02-2002"))),
                        hasProperty("endDate", equalTo(toInstant("12-03-2012")))
                ),
                allOf(
                        hasId(12_000L),
                        hasProperty("version", equalTo(3)),
                        hasProperty("versionDate", equalTo(toInstant("22-02-2007"))),
                        hasProperty("endDate", equalTo(toInstant("12-03-2014")))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasType() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPokemonType(asDictionary(PokemonType.class, 2L, "test-2"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPokemonType(asDictionary(PokemonType.class, 5L, "test-5"));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasDictionary("pokemonType", 2L, "test-2")
                ),
                allOf(
                        hasDictionary("pokemonType", 5L, "test-5")
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasPokemonElement() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPokemonElement(asDictionary(ElementType.class, 3L, "test-3"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPokemonElement(asDictionary(ElementType.class, 7L, "test-7"));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasDictionary("pokemonElement", 3L, "test-3")
                ),
                allOf(
                        hasDictionary("pokemonElement", 7L, "test-7")
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasStatus() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setStatus(asDictionary(PokemonStatus.class, 1L, "test-1"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setStatus(asDictionary(PokemonStatus.class, 2L, "test-2"));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasDictionary("status", 1L, "test-1")
                ),
                allOf(
                        hasDictionary("status", 2L, "test-2")
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasCatchPlace() {
        // arrange
        CatchPlace catchPlace1 = new CatchPlace();
        catchPlace1.setId(12_500L);
        catchPlace1.setPlaceName("test-place-name");
        catchPlace1.setCity("test-city");
        catchPlace1.setRegion(asDictionary(Region.class, 12L));

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCatchPlace(catchPlace1);

        CatchPlace catchPlace2 = new CatchPlace();
        catchPlace2.setId(15_500L);
        catchPlace2.setPlaceName("test-place-name-x");
        catchPlace2.setCity("test-city-x");
        catchPlace2.setRegion(asDictionary(Region.class, 14L, "test-14"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCatchPlace(catchPlace2);

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("catchPlace", allOf(
                                hasId(12_500L),
                                hasProperty("placeName", equalTo("test-place-name")),
                                hasProperty("city", equalTo("test-city")),
                                hasDictionary("region", 12L)
                        ))
                ),
                allOf(
                        hasProperty("catchPlace", allOf(
                                hasId(15_500L),
                                hasProperty("placeName", equalTo("test-place-name-x")),
                                hasProperty("city", equalTo("test-city-x")),
                                hasDictionary("region", 14L, "test-14")
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasCatchDate() {
        // arrange
        CatchDate catchDate1 = new CatchDate();
        catchDate1.setId(12_000L);
        catchDate1.setVersion(1);
        catchDate1.setCatchDate(LocalDate.of(1990, 5, 19));

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCatchDate(catchDate1);

        CatchDate catchDate2 = new CatchDate();
        catchDate2.setId(14_000L);
        catchDate2.setVersion(2);
        catchDate2.setCatchDate(LocalDate.of(2018, 5, 19));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCatchDate(catchDate2);

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("catchDate", allOf(
                                hasId(12_000L),
                                hasProperty("version", equalTo(1)),
                                hasProperty("catchDate", equalTo(LocalDate.of(1990, 5, 19)))
                        ))
                ),
                allOf(
                        hasProperty("catchDate", allOf(
                                hasId(14_000L),
                                hasProperty("version", equalTo(2)),
                                hasProperty("catchDate", equalTo(LocalDate.of(2018, 5, 19)))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasPokemonName() {
        // arrange
        PokemonName pokemonName1 = new PokemonName();
        pokemonName1.setId(13_000L);
        pokemonName1.setNickname("nick-name-value");
        pokemonName1.setName("name-value");
        pokemonName1.setTitle("title-value");

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPokemonName(pokemonName1);

        PokemonName pokemonName2 = new PokemonName();
        pokemonName2.setId(15_000L);
        pokemonName2.setNickname("nick-name-value-x");
        pokemonName2.setName("name-value-x");
        pokemonName2.setTitle("title-value-x");

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPokemonName(pokemonName2);

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("pokemonName", allOf(
                                hasId(13_000L),
                                hasProperty("nickname", equalTo("nick-name-value")),
                                hasProperty("name", equalTo("name-value")),
                                hasProperty("title", equalTo("title-value"))
                        ))
                ),
                allOf(
                        hasProperty("pokemonName", allOf(
                                hasId(15_000L),
                                hasProperty("nickname", equalTo("nick-name-value-x")),
                                hasProperty("name", equalTo("name-value-x")),
                                hasProperty("title", equalTo("title-value-x"))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasCombatStats() {
        // arrange
        CombatStats combatStats1 = new CombatStats();
        combatStats1.setId(11_000L);
        combatStats1.setHp(200);
        combatStats1.setAttack(12);
        combatStats1.setDefence(225);
        combatStats1.setSpecialAttack(18);
        combatStats1.setSpecialDefence(173);
        combatStats1.setSpeed(33);
        combatStats1.setEvasion(52);
        combatStats1.setAccuracy(14);

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCombatStats(combatStats1);

        CombatStats combatStats2 = new CombatStats();
        combatStats2.setId(12_000L);
        combatStats2.setHp(100);
        combatStats2.setAttack(25);
        combatStats2.setDefence(125);
        combatStats2.setSpecialAttack(74);
        combatStats2.setSpecialDefence(28);
        combatStats2.setSpeed(63);
        combatStats2.setEvasion(12);
        combatStats2.setAccuracy(85);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCombatStats(combatStats2);

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasProperty("combatStats", allOf(
                                hasId(11_000L),
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
                                hasId(12_000L),
                                hasProperty("hp", equalTo(100)),
                                hasProperty("attack", equalTo(25)),
                                hasProperty("defence", equalTo(125)),
                                hasProperty("specialAttack", equalTo(74)),
                                hasProperty("specialDefence", equalTo(28)),
                                hasProperty("speed", equalTo(63)),
                                hasProperty("evasion", equalTo(12)),
                                hasProperty("accuracy", equalTo(85))
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasActiveSkills() {
        // arrange
        ActiveSkill activeSkill1 = new ActiveSkill();
        activeSkill1.setId(10_000L);
        activeSkill1.setName("skill-1");
        activeSkill1.setSkillType(asDictionary(SkillType.class, 1L));
        activeSkill1.setElementType(asDictionary(ElementType.class, 2L));
        activeSkill1.setMinValue(10);
        activeSkill1.setMaxValue(22);

        ActiveSkill activeSkill2 = new ActiveSkill();
        activeSkill2.setId(11_000L);
        activeSkill2.setName("skill-2");
        activeSkill2.setSkillType(asDictionary(SkillType.class, 3L));
        activeSkill2.setElementType(asDictionary(ElementType.class, 4L));
        activeSkill2.setMinValue(8);
        activeSkill2.setMaxValue(25);

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setActiveSkills(Lists.newArrayList(activeSkill1, activeSkill2));

        ActiveSkill activeSkill3 = new ActiveSkill();
        activeSkill3.setId(12_000L);
        activeSkill3.setName("skill-3");
        activeSkill3.setSkillType(asDictionary(SkillType.class, 7L));
        activeSkill3.setElementType(asDictionary(ElementType.class, 12L));
        activeSkill3.setMinValue(3);
        activeSkill3.setMaxValue(73);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setActiveSkills(Lists.newArrayList(activeSkill3));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
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
                                        hasId(12_000L),
                                        hasProperty("name", equalTo("skill-3")),
                                        hasDictionary("skillType", 7L),
                                        hasDictionary("elementType", 12L),
                                        hasProperty("minValue", equalTo(3)),
                                        hasProperty("maxValue", equalTo(73))
                                )
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasPassiveSkills() {
        // arrange
        PassiveSkill passiveSkill1 = new PassiveSkill();
        passiveSkill1.setId(7_000L);
        passiveSkill1.setName("name-1");
        passiveSkill1.setBonusType(asDictionary(BonusType.class, 22L));
        passiveSkill1.setBonusValue(120);

        PassiveSkill passiveSkill2 = new PassiveSkill();
        passiveSkill2.setId(9_000L);
        passiveSkill2.setName("name-2");
        passiveSkill2.setBonusType(asDictionary(BonusType.class, 27L));
        passiveSkill2.setBonusValue(130);

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPassiveSkills(Lists.newArrayList(passiveSkill1, passiveSkill2));

        PassiveSkill passiveSkill3 = new PassiveSkill();
        passiveSkill3.setId(17_000L);
        passiveSkill3.setName("name-3");
        passiveSkill3.setBonusType(asDictionary(BonusType.class, 38L));
        passiveSkill3.setBonusValue(140);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPassiveSkills(Lists.newArrayList(passiveSkill3));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
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
                                        hasId(17_000L),
                                        hasProperty("name", equalTo("name-3")),
                                        hasDictionary("bonusType", 38L),
                                        hasProperty("bonusValue", equalTo(140))
                                )
                        ))
                )
        ));
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasCharacterStructure() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCharacterStructure(asDictionary(CharacterStructure.class, 15L, "test-value-15"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCharacterStructure(asDictionary(CharacterStructure.class, 17L, "test-value-17"));

        // act
        String json = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));
        List<Pokemon> result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertThat(result, containsInAnyOrder(
                allOf(
                        hasDictionary("characterStructure", 15L, "test-value-15")
                ),
                allOf(
                        hasDictionary("characterStructure", 17L, "test-value-17")
                )
        ));
    }
}
