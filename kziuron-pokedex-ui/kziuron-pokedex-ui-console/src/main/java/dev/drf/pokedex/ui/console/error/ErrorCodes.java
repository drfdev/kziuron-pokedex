package dev.drf.pokedex.ui.console.error;

public enum ErrorCodes {
    NULL_RESULT("null-result"),
    // authorization
    NULL_LOGIN_PASSWORD("null-login-password"),
    AUTHORIZATION_FAILED("auth-failed"),
    // exceptions
    INNER_ERROR("inner-error"),
    UNKNOWN_ERROR("unknown-error"),
    // parameters
    INCORRECT_PARAMETERS("incorrect-parameters"),
    NULL_API_RESULT("null-api-result"),
    ;

    private final String stringCode;

    ErrorCodes(String stringCode) {
        this.stringCode = stringCode;
    }

    public String getCode() {
        return stringCode;
    }
}
