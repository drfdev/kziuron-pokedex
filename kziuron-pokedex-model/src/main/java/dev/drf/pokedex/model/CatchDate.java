package dev.drf.pokedex.model;

import java.time.LocalDate;

public class CatchDate extends VersionedEntity {
    /**
     * Дата поимки покемона
     */
    private LocalDate catchDate;

    public LocalDate getCatchDate() {
        return catchDate;
    }

    public void setCatchDate(LocalDate catchDate) {
        this.catchDate = catchDate;
    }

    @Override
    public String toString() {
        return "CatchDate{" +
                "catchDate=" + catchDate +
                "} " + super.toString();
    }
}
