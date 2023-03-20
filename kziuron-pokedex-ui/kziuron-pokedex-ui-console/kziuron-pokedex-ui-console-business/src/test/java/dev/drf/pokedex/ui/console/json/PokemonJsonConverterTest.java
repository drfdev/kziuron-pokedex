package dev.drf.pokedex.ui.console.json;

import com.google.common.collect.Lists;
import dev.drf.pokedex.model.*;
import dev.drf.pokedex.model.dictionary.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.drf.pokedex.ui.console.MatcherUtils.hasDictionary;
import static dev.drf.pokedex.ui.console.MatcherUtils.hasId;
import static dev.drf.pokedex.ui.console.TestUtils.asDictionary;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class PokemonJsonConverterTest {
    private final PokemonJsonConverter converter = new PokemonJsonConverter();

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasSingleId() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setId(10_000L);

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertEquals(10_000L, result.getId());
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasBusinessEntityField() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setId(10_000L);
        pokemon.setVersion(2);
        pokemon.setVersionDate(toInstant("22-02-2002"));
        pokemon.setEndDate(toInstant("12-03-2012"));

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertEquals(10_000L, result.getId());
        assertEquals(2, result.getVersion());
        assertEquals(toInstant("22-02-2002"), result.getVersionDate());
        assertEquals(toInstant("12-03-2012"), result.getEndDate());
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasType() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonType(asDictionary(PokemonType.class, 2L, "test"));

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getPokemonType());
        assertEquals(2L, result.getPokemonType().getCode());
        assertEquals("test", result.getPokemonType().getName());
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasElementType() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setElementType(asDictionary(ElementType.class, 3L, "test"));

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getElementType());
        assertEquals(3L, result.getElementType().getCode());
        assertEquals("test", result.getElementType().getName());
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasStatus() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setStatus(asDictionary(PokemonStatus.class, 1L, "test-1"));

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getStatus());
        assertEquals(1L, result.getStatus().getCode());
        assertEquals("test-1", result.getStatus().getName());
    }

    @Test
    void shouldCorrectFullyConvert_whenPokemonHasCatchPlace() {
        // arrange
        CatchPlace catchPlace = new CatchPlace();
        catchPlace.setId(12_500L);
        catchPlace.setPlaceName("test-place-name");
        catchPlace.setCity("test-city");
        catchPlace.setRegion(asDictionary(Region.class, 12L));

        Pokemon pokemon = new Pokemon();
        pokemon.setCatchPlace(catchPlace);

        // act
        String json = converter.toJson(pokemon);
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
    void shouldCorrectFullyConvert_whenPokemonHasCatchDate() {
        // arrange
        CatchDate catchDate = new CatchDate();
        catchDate.setId(12_000L);
        catchDate.setVersion(1);
        catchDate.setCatchDate(LocalDate.of(1990, 5, 19));

        Pokemon pokemon = new Pokemon();
        pokemon.setCatchDate(catchDate);

        // act
        String json = converter.toJson(pokemon);
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
    void shouldCorrectFullyConvert_whenPokemonHasPokemonName() {
        // arrange
        PokemonName pokemonName = new PokemonName();
        pokemonName.setId(13_000L);
        pokemonName.setNickname("nick-name-value");
        pokemonName.setName("name-value");
        pokemonName.setTitle("title-value");

        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonName(pokemonName);

        // act
        String json = converter.toJson(pokemon);
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
    void shouldCorrectFullyConvert_whenPokemonHasCombatStats() {
        // arrange
        CombatStats combatStats = new CombatStats();
        combatStats.setId(11_000L);
        combatStats.setHp(200);
        combatStats.setAttack(12);
        combatStats.setDefence(225);
        combatStats.setSpecialAttack(18);
        combatStats.setSpecialDefence(173);
        combatStats.setSpeed(33);
        combatStats.setEvasion(52);
        combatStats.setAccuracy(14);

        Pokemon pokemon = new Pokemon();
        pokemon.setCombatStats(combatStats);

        // act
        String json = converter.toJson(pokemon);
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

        Pokemon pokemon = new Pokemon();
        pokemon.setActiveSkills(Lists.newArrayList(activeSkill1, activeSkill2));

        // act
        String json = converter.toJson(pokemon);
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

        Pokemon pokemon = new Pokemon();
        pokemon.setPassiveSkills(Lists.newArrayList(passiveSkill1, passiveSkill2));

        // act
        String json = converter.toJson(pokemon);
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
    void shouldCorrectFullyConvert_whenPokemonHasCharacterStructure() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setCharacterStructure(asDictionary(CharacterStructure.class, 15L, "test-value"));

        // act
        String json = converter.toJson(pokemon);
        Pokemon result = converter.parse(json);

        // assert
        assertNotNull(result);
        assertNotNull(result.getCharacterStructure());
        assertEquals(15L, result.getCharacterStructure().getCode());
        assertEquals("test-value", result.getCharacterStructure().getName());
    }
}
