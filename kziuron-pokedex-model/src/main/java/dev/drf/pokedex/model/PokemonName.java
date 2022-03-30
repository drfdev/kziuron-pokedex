package dev.drf.pokedex.model;

public class PokemonName extends VersionedEntity {
    /**
     * Имя покемона
     */
    private String name;
    /**
     * Дополнительный титул к имени покемона
     */
    private String title;
    /**
     * Кличка покемона
     */
    private String nickname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "PokemonName{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                "} " + super.toString();
    }
}
