package com.danielbobes.api.gorest.steps.posts;

import com.danielbobes.api.gorest.config.Endpoints;
import com.danielbobes.api.gorest.context.TestContext;
import com.danielbobes.api.gorest.data.factory.PostFactory;
import com.danielbobes.api.gorest.models.common.ErrorListResponse;
import com.danielbobes.api.gorest.models.common.MessageError;
import com.danielbobes.api.gorest.models.common.SingleError;
import com.danielbobes.api.gorest.models.posts.PostListResponse;
import com.danielbobes.api.gorest.models.posts.PostRequest;
import com.danielbobes.api.gorest.models.posts.PostResponse;
import com.danielbobes.api.gorest.utils.AssertionsMessages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PostSteps {

    private TestContext context;
    private PostListResponse postListResponse;
    private PostRequest postRequest;
    private PostResponse postResponse;

    public PostSteps(TestContext context) {
        this.context = context;
    }

    @And("que preparo una publicación válida")
    public void quePreparoUnaPublicacionValida() {
        Integer userId = Integer.parseInt(context.getUserId());
        postRequest = PostFactory.createValidPost(userId);
    }

    @And("que preparo una publicación con {string} y {string}")
    public void prepararPublicacionParametrizado(String title, String body) {
        Integer userId = Integer.parseInt(context.getUserId());
        postRequest = PostFactory.createParameterizedPost(userId, title, body);
    }

    @And("que preparo una publicación para un usuario con ID {int}")
    public void prepararPublicacionParaUsuarioInexistente(Integer idUsuarioInexistente){
        postRequest = PostFactory.createValidPost(idUsuarioInexistente);
    }

    @And("que tengo una publicación creada para ese usuario")
    public void crearPublicacionParaElUsuario(){
        quePreparoUnaPublicacionValida();
        crearPublicacion();
    }

    @When("realizo una petición petición para consultar la lista de publicaciones")
    public void consultarPublicaciones() {
        Response response = context.getRequest()
                .when()
                    .get(Endpoints.POSTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para consultar las publicaciones del usuario")
    public void consultarPublicacionesUsuario() {
        Response response = context.getRequest()
                .pathParam("userId", context.getUserId())
                .when()
                .get(Endpoints.USER_POSTS)
                .then()
                .log().all()
                .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para crear una publicación por el usuario")
    public void crearPublicacionUsuario() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .pathParam("userId", context.getUserId())
                    .body(postRequest)
                .when()
                    .post(Endpoints.USER_POSTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para crear la publicación")
    public void crearPublicacion() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .body(postRequest)
                .when()
                    .post(Endpoints.POSTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("envío una petición de creación con el campo {string} valor {string}")
    public void crearPublicacionConDatosInvalidos(String campo, String valorInvalido) {
        Map<String, Object> bodyInvalido = new HashMap<>();
        bodyInvalido.put("user_id", valorInvalido);
        bodyInvalido.put("title", "Título de prueba");
        bodyInvalido.put("body", "Contenido de prueba");

        RequestSpecification request = context.getRequest();
        Response response = request
                    .header("Content-Type", ContentType.JSON)
                    .body(bodyInvalido)
                .when()
                    .post(Endpoints.POSTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para consultar la publicación")
    public void consultarPublicacion() {
        Response response = context.getRequest()
                .when()
                    .pathParam("postId", context.getPostId())
                    .get(Endpoints.POST)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para modificar la publicación")
    public void modificarPublicacion(){
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .pathParam("postId", context.getPostId())
                    .body(postRequest)
                .when()
                    .put(Endpoints.POST)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para eliminar la publicación")
    public void eliminarPublicacion() {
        Response response = context.getRequest()
                    .pathParam("postId", context.getPostId())
                .when()
                    .delete(Endpoints.POST)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @And("la lista de publicaciones no esta vacía")
    public void validarListaDePublicacionesNoVacia(){
        assertNotNull(postListResponse, AssertionsMessages.PARSING_ERROR);
        assertFalse(postListResponse.isEmpty(), String.format(AssertionsMessages.EMPTY_LIST, "publicaciones"));
    }

    @And("la lista de publicaciones esta vacía")
    public void validarListaDePublicacionesVacia(){
        assertNotNull(postListResponse, AssertionsMessages.PARSING_ERROR);
        assertTrue(postListResponse.isEmpty(), String.format(AssertionsMessages.NOT_EMPTY_LIST, "publicaciones"));
    }

    @And("todas las publicaciones tienen los campos id, user_id, title y body")
    public void validarCamposPublicacionEnListaDePublicaciones(){
        postListResponse.getPostList().forEach(
            post -> assertAll(
                "Validando integridad de la publicación con ID: " + post.getId(),
                () -> assertAll("Validación del campo id",
                    () -> assertNotNull(post.getId(), String.format(AssertionsMessages.NULL_FIELD, "id")),
                    () -> assertTrue(post.getId() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "id"))),
                () -> assertAll("Validación del campo user_id",
                    () -> assertNotNull(post.getUser_id(), String.format(AssertionsMessages.NULL_FIELD, "user_id")),
                    () -> assertTrue(post.getId() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "user_id"))),
                () -> assertAll("Validación del campo title",
                    () -> assertNotNull(post.getTitle(), String.format(AssertionsMessages.NULL_FIELD, "title")),
                    () -> assertFalse(post.getTitle().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "title"))),
                () -> assertAll("Validación del campo body",
                    () -> assertNotNull(post.getBody(), String.format(AssertionsMessages.NULL_FIELD, "body")),
                    () -> assertFalse(post.getBody().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "body")))
            )
        );
    }

    @And("la respuesta contiene el identificador de la publicación")
    public void validarIdRespuesta(){
        assertNotNull(postResponse, AssertionsMessages.PARSING_ERROR);
        assertNotNull(postResponse.getId(), String.format(AssertionsMessages.NULL_FIELD, "id"));
        assertTrue(postResponse.getId() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "id"));
    }

    @And("la respuesta contiene los datos enviados en la petición al crear la publicación")
    @And("la respuesta contiene los datos enviados en la petición al modificar la publicación")
    public void validarRespuestaIncluyeCamposPeticion(){
        assertNotNull(postResponse, AssertionsMessages.PARSING_ERROR);

        assertAll("Validando que la respuesta coincide con la petición",
                () -> assertEquals(postRequest.getUser_id(), postResponse.getUser_id(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "user_id")),
                () -> assertEquals(postRequest.getTitle(), postResponse.getTitle(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "title")),
                () -> assertEquals(postRequest.getBody(), postResponse.getBody(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "body"))
        );
    }

    private void handleResponse(Response response, boolean expectList) {
        int statusCode = response.statusCode();
        switch (statusCode) {
            case HttpStatus.SC_OK -> {
                if(expectList){
                    postListResponse = PostListResponse.valueOf(response.as(new TypeRef<List<PostResponse>>(){}));
                } else {
                    postResponse = response.as(PostResponse.class);
                }
            }
            case HttpStatus.SC_CREATED -> {
                postResponse = response.as(PostResponse.class);
                context.setPostId(postResponse.getId());
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

}
