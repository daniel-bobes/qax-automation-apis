package com.danielbobes.api.gorest.exceptions;

public class MissingTestDataException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No se ha encontrado ningún usuario con los criterios definidos";

    public MissingTestDataException() {
        this(DEFAULT_MESSAGE);
    }

    public MissingTestDataException(String message) {
        super(message);
    }

}
