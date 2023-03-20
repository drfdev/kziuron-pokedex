package dev.drf.pokedex.ui.console.json;

import com.google.common.collect.Lists;
import dev.drf.pokedex.model.*;
import dev.drf.pokedex.model.dictionary.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.drf.pokedex.ui.console.TestUtils.asDictionary;
import static dev.drf.pokedex.ui.console.TestUtils.toInstant;
import static org.junit.jupiter.api.Assertions.*;

class PokemonJsonListConverterSerializeTest {
    private final PokemonJsonListConverter converter = new PokemonJsonListConverter();

    @Test
    void shouldCorrectConvertToJson_whenEmptyList() {
        // act
        String value = converter.toJson(Lists.newArrayList());

        // assert
        assertEquals("[]", value);
    }

    @Test
    void shouldCorrectConvertToJson_whenEmptyPokemonObject() {
        // arrange
        Pokemon pokemon = new Pokemon();

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon));

        // assert
        assertEquals("[{}]", value);
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasSingleId() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(10_000L);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(12_000L);

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);
        assertTrue(value.contains("\"id\":10000"));
        assertTrue(value.contains("\"id\":12000"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasBusinessEntityField() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(10_000L);
        pokemon1.setVersion(2);
        pokemon1.setVersionDate(toInstant("22-02-2002"));
        pokemon1.setEndDate(toInstant("12-03-2012"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(12_000L);
        pokemon2.setVersion(3);
        pokemon2.setVersionDate(toInstant("22-02-2005"));
        pokemon2.setEndDate(toInstant("12-03-2013"));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"id\":10000"));
        assertTrue(value.contains("\"version\":2"));
        assertTrue(value.contains("\"versionDate\":\"2002-02-22T00:00:00Z\""));
        assertTrue(value.contains("\"endDate\":\"2012-03-12T00:00:00Z\""));

        // pokemon 2
        assertTrue(value.contains("\"id\":12000"));
        assertTrue(value.contains("\"version\":3"));
        assertTrue(value.contains("\"versionDate\":\"2005-02-22T00:00:00Z\""));
        assertTrue(value.contains("\"endDate\":\"2013-03-12T00:00:00Z\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasType() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPokemonType(asDictionary(PokemonType.class, 2L, "test-2"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPokemonType(asDictionary(PokemonType.class, 4L, "test-4"));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"pokemonType\":{"));
        assertTrue(value.contains("\"code\":2"));
        assertTrue(value.contains("\"name\":\"test-2\""));

        // pokemon 2
        assertTrue(value.contains("\"pokemonType\":{"));
        assertTrue(value.contains("\"code\":4"));
        assertTrue(value.contains("\"name\":\"test-4\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasElementType() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setElementType(asDictionary(ElementType.class, 3L, "test-3"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setElementType(asDictionary(ElementType.class, 5L, "test-5"));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":3"));
        assertTrue(value.contains("\"name\":\"test-3\""));

        // pokemon 2
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":5"));
        assertTrue(value.contains("\"name\":\"test-5\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasStatus() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setStatus(asDictionary(PokemonStatus.class, 1L, "test-1"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setStatus(asDictionary(PokemonStatus.class, 2L, "test-2"));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"status\":{"));
        assertTrue(value.contains("\"code\":1"));
        assertTrue(value.contains("\"name\":\"test-1\""));

        // pokemon 2
        assertTrue(value.contains("\"status\":{"));
        assertTrue(value.contains("\"code\":2"));
        assertTrue(value.contains("\"name\":\"test-2\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCatchPlace() {
        // arrange
        CatchPlace catchPlace1 = new CatchPlace();
        catchPlace1.setId(12_500L);
        catchPlace1.setPlaceName("test-place-name-1");
        catchPlace1.setCity("test-city-1");
        catchPlace1.setRegion(asDictionary(Region.class, 12L));

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCatchPlace(catchPlace1);

        CatchPlace catchPlace2 = new CatchPlace();
        catchPlace2.setId(17_500L);
        catchPlace2.setPlaceName("test-place-name-2");
        catchPlace2.setCity("test-city-2");
        catchPlace2.setRegion(asDictionary(Region.class, 15L));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCatchPlace(catchPlace2);

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"catchPlace\":{"));
        assertTrue(value.contains("\"id\":12500"));
        assertTrue(value.contains("\"placeName\":\"test-place-name-1\""));
        assertTrue(value.contains("\"city\":\"test-city-1\""));

        assertTrue(value.contains("\"region\":{"));
        assertTrue(value.contains("\"code\":12"));

        // pokemon 2
        assertTrue(value.contains("\"catchPlace\":{"));
        assertTrue(value.contains("\"id\":17500"));
        assertTrue(value.contains("\"placeName\":\"test-place-name-2\""));
        assertTrue(value.contains("\"city\":\"test-city-2\""));

        assertTrue(value.contains("\"region\":{"));
        assertTrue(value.contains("\"code\":15"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCatchDate() {
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
        catchDate2.setCatchDate(LocalDate.of(1997, 5, 19));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCatchDate(catchDate2);

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"catchDate\":{"));
        assertTrue(value.contains("\"id\":12000"));
        assertTrue(value.contains("\"version\":1"));
        assertTrue(value.contains("\"catchDate\":\"1990-05-19\""));

        // pokemon 2
        assertTrue(value.contains("\"catchDate\":{"));
        assertTrue(value.contains("\"id\":14000"));
        assertTrue(value.contains("\"version\":2"));
        assertTrue(value.contains("\"catchDate\":\"1997-05-19\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasPokemonName() {
        // arrange
        PokemonName pokemonName1 = new PokemonName();
        pokemonName1.setId(13_000L);
        pokemonName1.setNickname("nick-name-value-1");
        pokemonName1.setName("name-value-1");
        pokemonName1.setTitle("title-value-1");

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPokemonName(pokemonName1);

        PokemonName pokemonName2 = new PokemonName();
        pokemonName2.setId(19_000L);
        pokemonName2.setNickname("nick-name-value-2");
        pokemonName2.setName("name-value-2");
        pokemonName2.setTitle("title-value-2");

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPokemonName(pokemonName2);

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"pokemonName\":{"));
        assertTrue(value.contains("\"id\":13000"));
        assertTrue(value.contains("\"nickname\":\"nick-name-value-1\""));
        assertTrue(value.contains("\"name\":\"name-value-1\""));
        assertTrue(value.contains("\"title\":\"title-value-1\""));

        // pokemon 2
        assertTrue(value.contains("\"pokemonName\":{"));
        assertTrue(value.contains("\"id\":19000"));
        assertTrue(value.contains("\"nickname\":\"nick-name-value-2\""));
        assertTrue(value.contains("\"name\":\"name-value-2\""));
        assertTrue(value.contains("\"title\":\"title-value-2\""));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCombatStats() {
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
        combatStats2.setHp(300);
        combatStats2.setAttack(14);
        combatStats2.setDefence(125);
        combatStats2.setSpecialAttack(28);
        combatStats2.setSpecialDefence(89);
        combatStats2.setSpeed(12);
        combatStats2.setEvasion(66);
        combatStats2.setAccuracy(21);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCombatStats(combatStats2);

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
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

        // pokemon 2
        assertTrue(value.contains("\"combatStats\":{"));
        assertTrue(value.contains("\"id\":12000"));
        assertTrue(value.contains("\"hp\":300"));
        assertTrue(value.contains("\"attack\":14"));
        assertTrue(value.contains("\"defence\":125"));
        assertTrue(value.contains("\"specialAttack\":28"));
        assertTrue(value.contains("\"specialDefence\":89"));
        assertTrue(value.contains("\"speed\":12"));
        assertTrue(value.contains("\"evasion\":66"));
        assertTrue(value.contains("\"accuracy\":21"));
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

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setActiveSkills(Lists.newArrayList(activeSkill1, activeSkill2));

        ActiveSkill activeSkill3 = new ActiveSkill();
        activeSkill3.setId(13_000L);
        activeSkill3.setName("skill-3");
        activeSkill3.setSkillType(asDictionary(SkillType.class, 8L));
        activeSkill3.setElementType(asDictionary(ElementType.class, 2L));
        activeSkill3.setMinValue(12);
        activeSkill3.setMaxValue(11);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setActiveSkills(Lists.newArrayList(activeSkill3));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"activeSkills\":["));

        // pokemon 1
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

        // pokemon 2
        assertTrue(value.contains("\"id\":13000"));
        assertTrue(value.contains("\"name\":\"skill-3\""));
        assertTrue(value.contains("\"skillType\":{"));
        assertTrue(value.contains("\"code\":8"));
        assertTrue(value.contains("\"elementType\":{"));
        assertTrue(value.contains("\"code\":2"));
        assertTrue(value.contains("\"minValue\":12"));
        assertTrue(value.contains("\"maxValue\":11"));
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

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPassiveSkills(Lists.newArrayList(passiveSkill1, passiveSkill2));

        PassiveSkill passiveSkill3 = new PassiveSkill();
        passiveSkill3.setId(4_000L);
        passiveSkill3.setName("name-4");
        passiveSkill3.setBonusType(asDictionary(BonusType.class, 8L));
        passiveSkill3.setBonusValue(117);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setPassiveSkills(Lists.newArrayList(passiveSkill3));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        assertTrue(value.contains("\"passiveSkills\":["));

        // pokemon 1
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

        // pokemon 2
        assertTrue(value.contains("\"id\":4000"));
        assertTrue(value.contains("\"name\":\"name-4\""));
        assertTrue(value.contains("\"bonusType\":{"));
        assertTrue(value.contains("\"code\":8"));
        assertTrue(value.contains("\"bonusValue\":117"));
    }

    @Test
    void shouldCorrectConvertToJson_whenPokemonHasCharacterStructure() {
        // arrange
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setCharacterStructure(asDictionary(CharacterStructure.class, 15L, "test-value-15"));

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setCharacterStructure(asDictionary(CharacterStructure.class, 17L, "test-value-17"));

        // act
        String value = converter.toJson(Lists.newArrayList(pokemon1, pokemon2));

        // assert
        assertNotNull(value);

        // pokemon 1
        assertTrue(value.contains("\"characterStructure\":{"));
        assertTrue(value.contains("\"code\":15"));
        assertTrue(value.contains("\"name\":\"test-value-15\""));

        // pokemon 2
        assertTrue(value.contains("\"characterStructure\":{"));
        assertTrue(value.contains("\"code\":17"));
        assertTrue(value.contains("\"name\":\"test-value-17\""));
    }
}
