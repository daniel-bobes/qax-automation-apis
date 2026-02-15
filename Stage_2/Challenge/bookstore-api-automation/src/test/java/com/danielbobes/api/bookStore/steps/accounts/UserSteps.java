package com.danielbobes.api.bookStore.steps.accounts;

import com.danielbobes.api.bookStore.config.Config;
import com.danielbobes.api.bookStore.config.Endpoints;
import com.danielbobes.api.bookStore.models.accounts.TokenResponse;
import com.danielbobes.api.bookStore.models.accounts.UserErrorResponse;
import com.danielbobes.api.bookStore.models.accounts.UserRequest;
import com.danielbobes.api.bookStore.models.accounts.UserResponse;
import com.danielbobes.api.bookStore.utils.Constants;
import com.danielbobes.api.bookStore.utils.RunContext;
import com.danielbobes.api.bookStore.utils.Utils;

import io.cucumber.java.AfterStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static com.danielbobes.api.bookStore.config.RequestSpecifications.*;

public class UserSteps {

    private String userName;
    private String password;
    private String userId;
    private String token;

    private RequestSpecification request;
    private Response response;

    private UserResponse userResponse;
    private UserErrorResponse errorResponse;
    private TokenResponse tokenResponse;

    @Given("que tengo un usuario {string} y contraseña {string} inválidos")
    public void queTengoDatosDeUsuarioInvalidos(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Given("el usuario prepara un request body válido para crear usuario")
    public void prepararRequestBody() {
        userName = Utils.generateUsername();
        password = Utils.generatePassword();
        RunContext.setCreds(userName, password);
    }

    @Given("el usuario tiene credenciales válidas")
    public void usuarioConCredencialesValidas() {
        if (!RunContext.hasCreds()) {
            prepararRequestBody();
            crearUsuario();
        } else {
            hydrateFromContext();
        }
    }

    @Given("que utilizo credenciales de acceso incorrectas")
    public void queUtilizoCredencialesDeAccesoIncorrectas() {
        userName = Utils.generateUsername();
        password = Utils.generatePassword();
    }

    @Given("el usuario tiene un token válido y un userId existente")
    public void crearUsuarioYObtenerToken() {
        // Garantiza estado ANTES del GET:
        if (!RunContext.hasUserId()) {
            // Si no hay userId, crea usuario con las credenciales persistidas (o genera nuevas si faltan)
            if (!RunContext.hasCreds()) {
                prepararRequestBody();
            } else {
                hydrateFromContext(); // refresca locales
            }
            crearUsuario();     // obtiene userId y lo persiste
            assertTrue(RunContext.hasUserId(), "No se pudo obtener userId");
        }
        if (!RunContext.hasToken()) {
            generarToken();     // obtiene token y lo persiste
            assertTrue(RunContext.hasToken(), "No se pudo obtener token");
        }
        hydrateFromContext();   // refresca locales
    }

    @Given("que el usuario prepara una petición {string}")
    public void quePreparoUnaPeticion(String tipoToken){
        usuarioConCredencialesValidas();

        if (tipoToken.equals("con token válido")) {
            request = authSpec(RunContext.orToken(token));
        } else if (tipoToken.equals("con token inválido")) {
            request = authSpec(Constants.FAKE_TOKEN);
        }
        // Sin token, no se añade el header
    }

    @When("realizo una petición para crear el usuario")
    public void crearUsuario() {
        UserRequest userRequest = new UserRequest(userName, password);

        response = baseSpec()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post(Endpoints.USER)
                .then()
                .log().all()
                .extract().response();

        switch (response.statusCode()) {
            case HttpStatus.SC_CREATED:
                userResponse = response.as(UserResponse.class);
                userId = userResponse.getUserId();
                RunContext.setUserId(userId);
                break;
            case HttpStatus.SC_BAD_REQUEST:
                errorResponse = response.as(UserErrorResponse.class);
                break;
        }

    }

    @When("realiza una petición para generar el token")
    public void generarToken() {
        UserRequest userRequest = new UserRequest(userName, password);

        response = baseSpec()
                    .contentType(ContentType.JSON)
                    .body(userRequest)
                .when()
                    .post(Endpoints.GENERAR_TOKEN)
                .then()
                    .log().all()
                    .extract().response();

        if (response.statusCode() == HttpStatus.SC_OK) {
            tokenResponse = response.as(TokenResponse.class);
            token = tokenResponse.getToken();
            RunContext.setToken(token);
        }
    }

    @When("realiza una petición GET a obtener información del usuario")
    public void obtenerInfoUsuario() {
        if (Objects.isNull(request)) {
            request = authSpec(RunContext.getToken());
        }

        userId = RunContext.hasUserId()? RunContext.getUserId(): userId;

        response = request
                    .pathParam("userId", userId)
                .when()
                    .get(Endpoints.GET_USER)
                .then()
                    .log().all()
                    .extract().response();

        switch (response.statusCode()) {
            case HttpStatus.SC_OK:
                userResponse = response.as(UserResponse.class);
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                errorResponse = response.as(UserErrorResponse.class);
                break;
        }
    }

    @Then("la API debe responder con un código {int}")
    public void apiDebeResponderConUnCodigo(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }

    @And("el cuerpo debe contener el mensaje de error {string}")
    public void elCuerpoDebeContenerMensajeDeErrorEsperado(String expectedMessage){
        assertNotNull(errorResponse, "Debe parsearse la respuesta");
        assertNotNull(errorResponse.getMessage(), "El mensaje de error debe tener valor");
        assertEquals(errorResponse.getMessage(), expectedMessage.trim(), "El mensaje de error obtenido no coincide con el esperado");
    }

    @And("el cuerpo debe contener el código de error {string}")
    public void elCuerpoDebeConteneCodigoDeErrorEsperado(String expectedCode){
        assertNotNull(errorResponse, "Debe parsearse la respuesta");
        assertNotNull(errorResponse.getCode(), "El código de error debe tener valor");
        assertEquals(errorResponse.getCode(), expectedCode.trim(), "El código de error obtenido no coincide con el esperado");
    }

    @And("el campo result debe mostrar el mensaje {string}")
    public void elCampoResultDebeMostrarElMensaje(String expectResult){
        assertNotNull(tokenResponse, "Debe parsearse la respuesta");
        assertNotNull(tokenResponse.getResult(), "El resultado debe tener valor");
        assertEquals(tokenResponse.getResult(), expectResult.trim(), "El resultado obtenido no coincide con el esperado");
    }

    @And("el campo status de la respuesta debe ser {string}")
    public void elCampoStatusDeLaRespuestaDebeSerFailed(String expectedStatus) {
        assertNotNull(tokenResponse, "Debe parsearse la respuesta");
        assertNotNull(tokenResponse.getStatus(), "El estado debe tener valor");
        assertEquals(tokenResponse.getStatus(), expectedStatus.trim(), "El estado obtenido no coincide con el esperado");
    }

    @And("la respuesta contiene un userId")
    public void validarUsuarioCreado() {
        assertNotNull(userResponse, "Debe parsearse la respuesta");
        assertNotNull(userResponse.getUserId(), "El userId debe tener valor");
    }

    @And("el userId corresponde al usuario creado")
    public void elUsuarioCreado() {
        assertEquals(userId, response.jsonPath().getString("userId"));
    }

    @And("la respuesta contiene un token")
    public void validarTokenCreado() {
        assertNotNull(tokenResponse, "Debe parsearse la respuesta");
        assertNotNull(tokenResponse.getToken(), "El token debe tener valor");
    }

    @And("el token no debe estar presente en la respuesta")
    public void elTokenNoDebeEstarPresenteEnLaRespuesta() {
        assertNotNull(tokenResponse, "Debe parsearse la respuesta");
        assertNull(tokenResponse.getToken(), "El token no debe tener valor");
    }

    private void hydrateFromContext() {
        if (userName == null && RunContext.getUserName() != null) userName = RunContext.getUserName();
        if (password == null && RunContext.getPassword() != null) password = RunContext.getPassword();
        if (userId   == null && RunContext.getUserId()   != null) userId   = RunContext.getUserId();
        if (token    == null && RunContext.getToken()    != null) token    = RunContext.getToken();
    }

    @AfterStep
    public void afterStep() {
        response = RunContext.hasResponse()? RunContext.getResponse() : response;
    }
}