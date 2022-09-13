package dev.drf.pokedex.common.error;

import dev.drf.pokedex.api.common.ApiErrorCode;

import javax.annotation.Nonnull;

public interface ErrorCode {
    @Nonnull
    String code();

    @Nonnull
    MetricCode metricCode();

    @Nonnull
    ApiErrorCode apiError();
}
