package dev.drf.pokedex.common.error;

import dev.drf.pokedex.api.common.ApiErrorCode;

import javax.annotation.Nonnull;

public enum CommonApiErrorCode implements ApiErrorCode {
    UNEXPECTED("unexpected")
    ;

    private final String code;

    CommonApiErrorCode(String code) {
        this.code = code;
    }

    @Nonnull
    @Override
    public String getErrorCode() {
        return code;
    }
}
