package dev.drf.pokedex.common.api;

import dev.drf.pokedex.api.common.ApiError;
import dev.drf.pokedex.api.common.ApiResult;
import dev.drf.pokedex.common.error.CommonApiErrorCode;
import dev.drf.pokedex.common.error.PokedexException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.Nonnull;

public abstract class CommonApiHandler {
    protected abstract System.Logger getLogger();

    protected <R> ApiResult<R> safeExecute(@Nonnull ApiExecutable<R> executable) {
        try {
            return ApiResult.success(executable.execute());
        } catch (PokedexException ex) {
            getLogger().log(System.Logger.Level.ERROR, "Api Execute error with code: {}", ex.getErrorCode(), ex);
            return ApiResult.error(
                    ApiError.of(
                            ex.getErrorCode().apiError(),
                            ExceptionUtils.getMessage(ex)));
        } catch (Exception ex) {
            getLogger().log(System.Logger.Level.ERROR, "Api Execute unknown error", ex);
            return ApiResult.error(
                    ApiError.of(
                            CommonApiErrorCode.UNEXPECTED,
                            ExceptionUtils.getMessage(ex)));
        }
    }
}
