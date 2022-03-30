package dev.drf.pokedex.model.dictionary;

import dev.drf.pokedex.model.BusinessEntity;

public abstract class Dictionary extends BusinessEntity {
    private long code;
    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "code=" + code +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
