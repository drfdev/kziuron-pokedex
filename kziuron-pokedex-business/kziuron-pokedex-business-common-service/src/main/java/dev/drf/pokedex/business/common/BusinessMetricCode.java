package dev.drf.pokedex.business.common;

import dev.drf.pokedex.common.error.MetricCode;

import javax.annotation.Nonnull;

public enum BusinessMetricCode implements MetricCode {
    NOT_FOUND("not-found")
    ;

    private final String code;

    BusinessMetricCode(String code) {
        this.code = code;
    }

    @Nonnull
    @Override
    public String metricCode() {
        return code;
    }
}
