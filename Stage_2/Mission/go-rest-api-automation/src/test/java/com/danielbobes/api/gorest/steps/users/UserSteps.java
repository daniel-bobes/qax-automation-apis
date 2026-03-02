package com.danielbobes.api.gorest.steps.users;

import com.danielbobes.api.gorest.config.Endpoints;
import com.danielbobes.api.gorest.context.TestContext;
import com.danielbobes.api.gorest.data.factory.UserFactory;
import com.danielbobes.api.gorest.models.common.ErrorListResponse;
import com.danielbobes.api.gorest.models.common.MessageError;
import com.danielbobes.api.gorest.models.common.SingleError;
import com.danielbobes.api.gorest.models.users.UserListResponse;
import com.danielbobes.api.gorest.models.users.UserRequest;
import com.danielbobes.api.gorest.models.users.UserResponse;
import com.danielbobes.api.gorest.utils.AssertionsMessages;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps {

    private TestContext context;
    private UserListResponse userListResponse;
    private UserRequest userRequest;
    private UserResponse userResponse;

    public UserSteps(TestContext context) {
        this.context = context;
    }

    @Given("que preparo un usuario con {string}, {string}, {string} y {string}")
    public void prepararUsuarioParametrizado(String name, String email, String gender, String status) {
        userRequest = UserFactory.createParameterizedUser(name, email, gender, status);
    }

    @Given("que preparo un usuario válido")
    public void prepararUsuarioValido(){
        userRequest = UserFactory.createValidUser();
    }

    @Given("que preparo un usuario con el mismo email que el anterior")
    public void prepararUsuarioConEmailYaRegistrado(){
        getOrCreateUser();
        prepararUsuarioValido();
        userRequest.setEmail(context.getLastUsedEmail());
    }

    @Given("que tengo un usuario creado anteriormente")
    public void getOrCreateUser(){
        if(!context.hasUserId()){
            prepararUsuarioValido();
            crearUsuario();
            assertNotNull(context.getUserId(), "No se pudo obtener el 'id'");
        }
    }

    @And("que obtengo un email de otro usuario ya existente")
    public void obtenerEmailUsuarioExistente() {
        consultarListadoDeUsuarios();

        String emailExistente = userListResponse.getOneUsedEmail(context.getLastUsedEmail());

        if (Objects.isNull(userRequest)) {
            userRequest = UserFactory.createValidUser();
        }
        userRequest.setEmail(emailExistente);
    }

    @When("realizo una petición petición para consultar la lista de usuarios")
    public void consultarListadoDeUsuarios() {
        Response response = context.getRequest()
                .when()
                    .get(Endpoints.USERS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para crear un usuario")
    public void crearUsuario() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .body(userRequest)
                .when()
                    .post(Endpoints.USERS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para consultar el usuario")
    public void consultarUsuario(){
        Response response = context.getRequest()
                    .pathParam("userId", context.getUserId())
                .when()
                    .get(Endpoints.USER)
                .then()
                    .log().all()
                .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para modificar el usuario")
    public void modificarUsuario(){
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .pathParam("userId", context.getUserId())
                    .body(userRequest)
                .when()
                    .put(Endpoints.USER)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para eliminar el usuario")
    public void eliminarUsuario(){
        Response response = context.getRequest()
                    .pathParam("userId", context.getUserId())
                .when()
                    .delete(Endpoints.USER)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @And("la lista de usuarios no esta vacía")
    public void validarListaDeUsuariosNoVacia(){
        assertNotNull(userListResponse, AssertionsMessages.PARSING_ERROR);
        assertFalse(userListResponse.isEmpty(), String.format(AssertionsMessages.EMPTY_LIST, "usuarios"));
    }

    @And("todos los usuarios tienen los campos id, name, email, gender y status")
    public void validarCamposUsuarioEnListaDeUsuarios(){
        userListResponse.getUserList().forEach(
            user -> assertAll(
                "Validando integridad del usuario con ID: " + user.getId(),
                () -> assertAll("Validación del campo id",
                    () -> assertNotNull(user.getId(), String.format(AssertionsMessages.NULL_FIELD, "id")),
                    () -> assertFalse(user.getId().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "id")),
                    () -> assertTrue(user.getId().matches("\\d+"), String.format(AssertionsMessages.NON_NUMERIC_FIELD, "id"))),
                () -> assertAll("Validación del campo nombre",
                    () -> assertNotNull(user.getName(), String.format(AssertionsMessages.NULL_FIELD, "name")),
                    () -> assertFalse(user.getName().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "name"))),
                () -> assertAll("Validación del campo email",
                    () -> assertNotNull(user.getEmail(), String.format(AssertionsMessages.NULL_FIELD, "email")),
                    () -> assertFalse(user.getEmail().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "email"))),
                () -> assertAll("Validación del campo gender",
                    () -> assertNotNull(user.getGender(), String.format(AssertionsMessages.NULL_FIELD, "gender")),
                    () -> assertFalse(user.getGender().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "gender"))),
                () -> assertAll("Validación del campo status",
                    () -> assertNotNull(user.getStatus(), String.format(AssertionsMessages.NULL_FIELD, "status")),
                    () -> assertFalse(user.getStatus().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "status")))
            )
        );
    }

    @And("la respuesta contiene un identificador de usuario")
    public void validarIdRespuesta(){
        assertNotNull(userResponse, AssertionsMessages.PARSING_ERROR);
        assertNotNull(userResponse.getId(), String.format(AssertionsMessages.NULL_FIELD, "id"));
        assertTrue(Long.parseLong(userResponse.getId()) > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "id"));
    }

    @And("la respuesta contiene los datos enviados en la petición")
    public void validarRespuestaIncluyeCamposPeticion(){
        assertNotNull(userResponse, AssertionsMessages.PARSING_ERROR);

        assertAll("Validando que la respuesta coincide con la petición",
                () -> assertEquals(userRequest.getName(), userResponse.getName(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "name")),
                () -> assertEquals(userRequest.getEmail(), userResponse.getEmail(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "email")),
                () -> assertEquals(userRequest.getGender(), userResponse.getGender(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "gender")),
                () -> assertEquals(userRequest.getStatus(), userResponse.getStatus(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "status"))
        );
    }

    private void handleResponse(Response response, boolean expectList) {
        int statusCode = response.statusCode();
        switch (statusCode) {
            case HttpStatus.SC_OK -> {
                if(expectList){
                    userListResponse = UserListResponse.valueOf(response.as(new TypeRef<List<UserResponse>>(){}));
                } else {
                    userResponse = response.as(UserResponse.class);
                }
            }
            case HttpStatus.SC_CREATED -> {
                userResponse = response.as(UserResponse.class);
                context.setUserId(userResponse.getId());
                context.setLastUsedEmail(userResponse.getEmail());
            }
            case HttpStatus.SC_UNAUTHORIZED, HttpStatus.SC_NOT_FOUND
                    ->  context.setMessageError(response.as(MessageError.class));
            case HttpStatus.SC_UNPROCESSABLE_ENTITY -> {
                ErrorListResponse errorListResponse = ErrorListResponse.valueOf(
                        response.as(new TypeRef<List<SingleError>>() {}));
                context.setErrorListResponse(errorListResponse);
            }
        }
    }

    @Before
    public void cleanLocalState() {
        userRequest = null;
        userResponse = null;
        userListResponse = null;
    }

}
