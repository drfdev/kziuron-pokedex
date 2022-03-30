package dev.drf.pokedex.model;

public abstract class BusinessEntity implements IdentifiableEntity {
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BusinessEntity{" +
                "id=" + id +
                '}';
    }
}
