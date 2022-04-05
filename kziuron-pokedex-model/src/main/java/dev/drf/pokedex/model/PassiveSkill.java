package dev.drf.pokedex.model;

import dev.drf.pokedex.model.dictionary.BonusType;

public class PassiveSkill extends BusinessEntity {
    /**
     * Наименование умения
     */
    private String name;
    /**
     * Тип бонуса, гарантируемого умением
     */
    private BonusType bonusType;
    /**
     * Значение бонуса
     */
    private Integer bonusValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public Integer getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(Integer bonusValue) {
        this.bonusValue = bonusValue;
    }

    @Override
    public String toString() {
        return "PassiveSkill{" +
                "name='" + name + '\'' +
                ", bonusType=" + bonusType +
                ", bonusValue=" + bonusValue +
                "} " + super.toString();
    }
}
