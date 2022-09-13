package dev.drf.pokedex.business.common;

import dev.drf.pokedex.api.common.ApiErrorCode;
import dev.drf.pokedex.common.error.ErrorCode;
import dev.drf.pokedex.common.error.MetricCode;

import javax.annotation.Nonnull;

public enum BusinessErrorCode implements ErrorCode, ApiErrorCode {
    POKEMON_NOT_FOUND("pokemon-not-found", BusinessMetricCode.NOT_FOUND),
    ;

    private final String code;
    private final MetricCode metricCode;

    BusinessErrorCode(@Nonnull String code,
                      @Nonnull MetricCode metricCode) {
        this.code = code;
        this.metricCode = metricCode;
    }

    @Nonnull
    @Override
    public String code() {
        return code;
    }

    @Nonnull
    @Override
    public MetricCode metricCode() {
        return metricCode;
    }

    @Nonnull
    @Override
    public ApiErrorCode apiError() {
        return this;
    }

    @Nonnull
    @Override
    public String getErrorCode() {
        return code;
    }
}
