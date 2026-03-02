package com.danielbobes.api.gorest.utils;

public final class AssertionsMessages {

    // Constantes para deserialización respuestas
    public static final String PARSING_ERROR = "La respuesta de la API no ha sido deserializada";

    // Mensajes de validación listas
    public static final String EMPTY_LIST = "La lista de %s no debe estar vacía";
    public static final String NOT_EMPTY_LIST = "La lista de %s debe estar vacía";
    public static final String WRONG_LIST_SIZE = "La lista de %s debería contener %s elementos";

    // Mensajes de estado HTTP
    public static final String STATUS_CODE_MISMATCH = "El código de estado de la respuesta no es el esperado";

    // Mensajes de validación de campos
    public static final String NULL_FIELD = "El campo '%s' no debe ser nulo";
    public static final String EMPTY_FIELD = "El campo '%s' no debe estar vacío";
    public static final String FIELD_MISMATCH = "El campo '%s' no coincide";
    public static final String NON_POSITIVE_FIELD =  "El campo '%s' debe ser positivo";
    public static final String NON_NUMERIC_FIELD =  "El campo '%s' debería ser numérico";

    // Mensajes de validación de errores
    public static final String EXPECTED_ERROR_NOT_FOUND = "No se ha encontrado el error: %s -> %s";

    private AssertionsMessages() {

    }

}
