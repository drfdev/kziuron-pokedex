package dev.drf.pokedex.model;

import java.time.Instant;

public abstract class BusinessEntity extends VersionedEntity {
    private Instant endDate;

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BusinessEntity{" +
                "endDate=" + endDate +
                "} " + super.toString();
    }
}
