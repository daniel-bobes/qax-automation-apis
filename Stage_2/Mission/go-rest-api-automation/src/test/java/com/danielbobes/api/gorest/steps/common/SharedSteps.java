package com.danielbobes.api.gorest.steps.common;

import com.danielbobes.api.gorest.config.enums.AuthType;
import com.danielbobes.api.gorest.utils.AssertionsMessages;
import com.danielbobes.api.gorest.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class SharedSteps {

    private TestContext context;

    public SharedSteps(TestContext context) {
        this.context = context;
    }


    @Given("que uso un tipo de autenticación {string}")
    public void prepararPeticionConAlgo(String tipoToken){
        AuthType authType = AuthType.fromSpanishDescription(tipoToken);
        context.setTokenType(authType);
    }

    @And("que utilizo un ID de {string} inexistente {int}")
    public void queUtilizoUnIdDeComentarioInexistente(String tipoEntidad, Integer idInexistente) {
        switch (tipoEntidad) {
            case "usuario" -> context.setUserId(String.valueOf(idInexistente));
            case "publicación" -> context.setPostId(idInexistente);
            case "comentario" -> context.setCommentId(idInexistente);
            default -> throw new IllegalArgumentException("Entidad no soportada: " + tipoEntidad);
        }
    }

    @Then("la API responde con un código de estado {int}")
    public void validarCodigoEstadoRespuesta(int expectedStatusCode){
        assertEquals(expectedStatusCode, context.getResponse().getStatusCode(),
                AssertionsMessages.STATUS_CODE_MISMATCH);
    }

    @And("la respuesta contiene el mensaje {string}")
    public void validarMensajeErrorRespuesta(String expectedErrorMessage){
        assertNotNull(context.getMessageError(), AssertionsMessages.PARSING_ERROR);
        assertEquals(expectedErrorMessage, context.getMessageError().getMessage(),
                String.format(AssertionsMessages.FIELD_MISMATCH, "message"));
    }

    @And("la respuesta de error contiene el campo {string} y el mensaje {string}")
    public void validarMensajeDeErrorEspecifico(String expectedField, String expectedMessage){
        assertNotNull(context.getErrorListResponse(), AssertionsMessages.PARSING_ERROR);

        boolean foundOne =
                context.getErrorListResponse().getErrorList()
                        .stream()
                        .anyMatch(singleError ->
                                singleError.getField().equalsIgnoreCase(expectedField)
                                        && singleError.getMessage().trim().contains(expectedMessage)
                        );
        assertTrue(foundOne,
                () -> String.format(AssertionsMessages.EXPECTED_ERROR_NOT_FOUND, expectedField, expectedMessage));
    }

}
