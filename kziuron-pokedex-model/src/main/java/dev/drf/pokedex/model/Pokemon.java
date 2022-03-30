package dev.drf.pokedex.model;

import dev.drf.pokedex.model.dictionary.CharacterStructure;
import dev.drf.pokedex.model.dictionary.ElementType;
import dev.drf.pokedex.model.dictionary.PokemonStatus;
import dev.drf.pokedex.model.dictionary.PokemonType;

import java.util.List;

/**
 * Описание покемона
 */
public class Pokemon extends VersionedEntity {
    /**
     * Тип покемона
     */
    private PokemonType pokemonType;
    /**
     * Стихия к которой покемон относится
     */
    private ElementType elementType;
    /**
     * Статус покемона
     */
    private PokemonStatus status;
    /**
     * Место, где покемон был пойман
     */
    private CatchPlace catchPlace;
    /**
     * Время поимки покемона
     */
    private CatchDate catchDate;
    /**
     * Имя покемона
     */
    private PokemonName pokemonName;
    /**
     * Боевые характеристики покемона
     */
    private CombatStats combatStats;
    /**
     * Активные умения покемона
     */
    private List<ActiveSkill> activeSkills;
    /**
     * Пассивные умения покемона
     */
    private List<PassiveSkill> passiveSkills;
    /**
     * Характер покемона
     */
    private CharacterStructure characterStructure;

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public ElementType getPokemonElement() {
        return elementType;
    }

    public void setPokemonElement(ElementType elementType) {
        this.elementType = elementType;
    }

    public PokemonStatus getStatus() {
        return status;
    }

    public void setStatus(PokemonStatus status) {
        this.status = status;
    }

    public CatchPlace getCatchPlace() {
        return catchPlace;
    }

    public void setCatchPlace(CatchPlace catchPlace) {
        this.catchPlace = catchPlace;
    }

    public CatchDate getCatchDate() {
        return catchDate;
    }

    public void setCatchDate(CatchDate catchDate) {
        this.catchDate = catchDate;
    }

    public PokemonName getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(PokemonName pokemonName) {
        this.pokemonName = pokemonName;
    }

    public CombatStats getCombatStats() {
        return combatStats;
    }

    public void setCombatStats(CombatStats combatStats) {
        this.combatStats = combatStats;
    }

    public List<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }

    public void setActiveSkills(List<ActiveSkill> activeSkills) {
        this.activeSkills = activeSkills;
    }

    public List<PassiveSkill> getPassiveSkills() {
        return passiveSkills;
    }

    public void setPassiveSkills(List<PassiveSkill> passiveSkills) {
        this.passiveSkills = passiveSkills;
    }

    public CharacterStructure getCharacterStructure() {
        return characterStructure;
    }

    public void setCharacterStructure(CharacterStructure characterStructure) {
        this.characterStructure = characterStructure;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "pokemonType=" + pokemonType +
                ", pokemonElement=" + elementType +
                ", status=" + status +
                ", catchPlace=" + catchPlace +
                ", catchDate=" + catchDate +
                ", pokemonName=" + pokemonName +
                ", combatStats=" + combatStats +
                ", activeSkills=" + activeSkills +
                ", passiveSkills=" + passiveSkills +
                ", characterStructure=" + characterStructure +
                "} " + super.toString();
    }
}
