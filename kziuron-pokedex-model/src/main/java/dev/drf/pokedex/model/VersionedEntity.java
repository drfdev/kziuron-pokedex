package dev.drf.pokedex.model;

import java.time.Instant;

public abstract class VersionedEntity extends BaseEntity {
    private Integer version;
    private Instant versionDate;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Instant versionDate) {
        this.versionDate = versionDate;
    }

    @Override
    public String toString() {
        return "VersionedEntity{" +
                "version=" + version +
                ", versionDate=" + versionDate +
                "} " + super.toString();
    }
}
