package dev.drf.pokedex.model;

import dev.drf.pokedex.model.dictionary.Region;

public class CatchPlace extends VersionedEntity {
    /**
     * Регион, в котором покемон был пойман
     */
    private Region region;
    /**
     * Ближайший город
     */
    private String city;
    /**
     * Наименование места поимки
     */
    private String placeName;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public String toString() {
        return "CatchPlace{" +
                "region=" + region +
                ", city='" + city + '\'' +
                ", placeName='" + placeName + '\'' +
                "} " + super.toString();
    }
}
