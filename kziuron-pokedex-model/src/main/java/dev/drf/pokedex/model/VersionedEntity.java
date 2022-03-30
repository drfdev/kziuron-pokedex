package dev.drf.pokedex.model;

import java.time.Instant;

public abstract class VersionedEntity extends BusinessEntity {
    private Integer version;
    private Instant versionDate;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VersionedEntity{" +
                "version=" + version +
                ", versionDate=" + versionDate +
                "} " + super.toString();
    }
}
