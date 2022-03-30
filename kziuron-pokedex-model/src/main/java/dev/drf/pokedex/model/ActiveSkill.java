package dev.drf.pokedex.model;

import dev.drf.pokedex.model.dictionary.ElementType;
import dev.drf.pokedex.model.dictionary.SkillType;

public class ActiveSkill extends VersionedEntity {
    /**
     * Наименование умения
     */
    private String name;
    /**
     * Тип умения
     */
    private SkillType skillType;
    /**
     * Стихия умения
     */
    private ElementType elementType;
    /**
     * Минимальное значение умения
     */
    private Integer minValue;
    /**
     * Максимальное значение умения
     */
    private Integer maxValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "ActiveSkill{" +
                "name='" + name + '\'' +
                ", skillType=" + skillType +
                ", elementType=" + elementType +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                "} " + super.toString();
    }
}
