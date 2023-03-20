package dev.drf.pokedex.ui.console.json;

import com.google.common.collect.Lists;
import dev.drf.pokedex.model.*;
import dev.drf.pokedex.model.dictionary.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.drf.pokedex.ui.console.TestUtils.asDictionary;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.junit.jupiter.api.Assertions.*;

class PokemonJsonConverterSerializeTest {
    private final PokemonJsonConverter converter = new PokemonJsonConverter();

    @Test
    void shouldCorrectConvertToJson_whenEmptyPokemonObject() {
        // arrange
        Pokemon pokemon = new Pokemon();

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertEquals("{}", value);
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasSingleId() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setId(10_000L);

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"id\":10000"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasBusinessEntityField() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setId(10_000L);
        pokemon.setVersion(2);
        pokemon.setVersionDate(toInstant("22-02-2002"));
        pokemon.setEndDate(toInstant("12-03-2012"));

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"id\":10000"));
        assertTrue(value.contains("\"version\":2"));
        assertTrue(value.contains("\"versionDate\":\"2002-02-22T00:00:00Z\""));
        assertTrue(value.contains("\"endDate\":\"2012-03-12T00:00:00Z\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasType() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonType(asDictionary(PokemonType.class, 2L, "test"));

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"pokemonType\":{"));
        assertTrue(value.contains("\"code\":2"));
        assertTrue(value.contains("\"name\":\"test\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasElementType() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setElementType(asDictionary(ElementType.class, 3L, "test"));

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":3"));
        assertTrue(value.contains("\"name\":\"test\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasStatus() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setStatus(asDictionary(PokemonStatus.class, 1L, "test-1"));

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"status\":{"));
        assertTrue(value.contains("\"code\":1"));
        assertTrue(value.contains("\"name\":\"test-1\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCatchPlace() {
        // arrange
        CatchPlace catchPlace = new CatchPlace();
        catchPlace.setId(12_500L);
        catchPlace.setPlaceName("test-place-name");
        catchPlace.setCity("test-city");
        catchPlace.setRegion(asDictionary(Region.class, 12L));

        Pokemon pokemon = new Pokemon();
        pokemon.setCatchPlace(catchPlace);

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"catchPlace\":{"));
        assertTrue(value.contains("\"id\":12500"));
        assertTrue(value.contains("\"placeName\":\"test-place-name\""));
        assertTrue(value.contains("\"city\":\"test-city\""));

        assertTrue(value.contains("\"region\":{"));
        assertTrue(value.contains("\"code\":12"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCatchDate() {
        // arrange
        CatchDate catchDate = new CatchDate();
        catchDate.setId(12_000L);
        catchDate.setVersion(1);
        catchDate.setCatchDate(LocalDate.of(1990, 5, 19));

        Pokemon pokemon = new Pokemon();
        pokemon.setCatchDate(catchDate);

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"catchDate\":{"));
        assertTrue(value.contains("\"id\":12000"));
        assertTrue(value.contains("\"version\":1"));
        assertTrue(value.contains("\"catchDate\":\"1990-05-19\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasPokemonName() {
        // arrange
        PokemonName pokemonName = new PokemonName();
        pokemonName.setId(13_000L);
        pokemonName.setNickname("nick-name-value");
        pokemonName.setName("name-value");
        pokemonName.setTitle("title-value");

        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonName(pokemonName);

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"pokemonName\":{"));
        assertTrue(value.contains("\"id\":13000"));
        assertTrue(value.contains("\"nickname\":\"nick-name-value\""));
        assertTrue(value.contains("\"name\":\"name-value\""));
        assertTrue(value.contains("\"title\":\"title-value\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCombatStats() {
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
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"combatStats\":{"));
        assertTrue(value.contains("\"id\":11000"));
        assertTrue(value.contains("\"hp\":200"));
        assertTrue(value.contains("\"attack\":12"));
        assertTrue(value.contains("\"defence\":225"));
        assertTrue(value.contains("\"specialAttack\":18"));
        assertTrue(value.contains("\"specialDefence\":173"));
        assertTrue(value.contains("\"speed\":33"));
        assertTrue(value.contains("\"evasion\":52"));
        assertTrue(value.contains("\"accuracy\":14"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasActiveSkills() {
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
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"activeSkills\":["));

        assertTrue(value.contains("\"id\":10000"));
        assertTrue(value.contains("\"name\":\"skill-1\""));
        assertTrue(value.contains("\"skillType\":{"));
        assertTrue(value.contains("\"code\":1"));
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":2"));
        assertTrue(value.contains("\"minValue\":10"));
        assertTrue(value.contains("\"maxValue\":22"));

        assertTrue(value.contains("\"id\":11000"));
        assertTrue(value.contains("\"name\":\"skill-2\""));
        assertTrue(value.contains("\"skillType\":{"));
        assertTrue(value.contains("\"code\":3"));
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":4"));
        assertTrue(value.contains("\"minValue\":8"));
        assertTrue(value.contains("\"maxValue\":25"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasPassiveSkills() {
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
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"passiveSkills\":["));

        assertTrue(value.contains("\"id\":7000"));
        assertTrue(value.contains("\"name\":\"name-1\""));
        assertTrue(value.contains("\"bonusType\":{"));
        assertTrue(value.contains("\"code\":22"));
        assertTrue(value.contains("\"bonusValue\":120"));

        assertTrue(value.contains("\"id\":9000"));
        assertTrue(value.contains("\"name\":\"name-2\""));
        assertTrue(value.contains("\"bonusType\":{"));
        assertTrue(value.contains("\"code\":27"));
        assertTrue(value.contains("\"bonusValue\":130"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCharacterStructure() {
        // arrange
        Pokemon pokemon = new Pokemon();
        pokemon.setCharacterStructure(asDictionary(CharacterStructure.class, 15L, "test-value"));

        // act
        String value = converter.toJson(pokemon);

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"characterStructure\":{"));
        assertTrue(value.contains("\"code\":15"));
        assertTrue(value.contains("\"name\":\"test-value\""));
    }
}
