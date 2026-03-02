package com.danielbobes.api.gorest.config.enums;

import com.danielbobes.api.gorest.exceptions.InvalidTokenConfigurationException;

import java.util.Arrays;

public enum AuthType {
    VALID("válido"), INVALID("inválido"), ABSENT("ausente");

    private static final AuthType[] AUTH_TYPES = AuthType.values();

    private final String spanishDescription;

    AuthType(String description) {
        this.spanishDescription = description;
    }

    public String getSpanishDescription() {
        return spanishDescription;
    }

    public static AuthType fromSpanishDescription(String description){
        return Arrays.stream(AUTH_TYPES)
                .filter(type -> type.getSpanishDescription().equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new InvalidTokenConfigurationException(description));
    }
}
