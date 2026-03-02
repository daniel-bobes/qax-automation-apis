package com.danielbobes.api.gorest.exceptions;

public class InvalidTokenConfigurationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Error: El tipo de token '%s' no es válido para este framework.";

    public InvalidTokenConfigurationException(String tokenType) {
        super(String.format(DEFAULT_MESSAGE, tokenType));
    }

}
