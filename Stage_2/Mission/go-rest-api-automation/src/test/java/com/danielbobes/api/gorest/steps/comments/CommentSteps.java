package com.danielbobes.api.gorest.steps.comments;

import com.danielbobes.api.gorest.config.Endpoints;
import com.danielbobes.api.gorest.context.TestContext;
import com.danielbobes.api.gorest.data.factory.CommentFactory;
import com.danielbobes.api.gorest.models.comments.CommentListResponse;
import com.danielbobes.api.gorest.models.comments.CommentRequest;
import com.danielbobes.api.gorest.models.comments.CommentResponse;
import com.danielbobes.api.gorest.models.common.ErrorListResponse;
import com.danielbobes.api.gorest.models.common.MessageError;
import com.danielbobes.api.gorest.models.common.SingleError;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentSteps {

    private TestContext context;
    private CommentListResponse commentListResponse;
    private CommentRequest commentRequest;
    private CommentResponse commentResponse;

    public CommentSteps(TestContext context) {
        this.context = context;
    }

    @And("que preparo un comentario válido")
    public void quePreparoUnComentarioValido() {
        commentRequest = CommentFactory.createValidComment(context.getPostId());
    }

    @And("que preparo un comentario con {string}, {string} y {string}")
    public void prepararPublicacionParametrizado(String name, String email, String body) {
        commentRequest = CommentFactory.createParameterizedComment(context.getPostId(), name, email, body);
    }

    @And("que preparo un comentario para una publicación con ID {int}")
    public void prepararPublicacionParaUsuarioInexistente(Integer idPublicacionInexistente){
        commentRequest = CommentFactory.createValidComment(idPublicacionInexistente);
    }

    @And("que tengo un comentario creado para esa publicación")
    public void crearComentarioParaElUsuario(){
        quePreparoUnComentarioValido();
        crearComentario();
    }

    @When("realizo una petición petición para consultar la lista de comentarios")
    public void consultarComentarios() {
        Response response = context.getRequest()
                .when()
                    .get(Endpoints.COMMENTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para crear el comentario")
    public void crearComentario() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .body(commentRequest)
                .when()
                    .post(Endpoints.COMMENTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para crear el comentario con el campo {string} informado con el valor {string}")
    public void crearComentarioConDatosInvalidos(String campo, String valorInvalido) {
        Map<String, Object> bodyInvalido = new HashMap<>();
        bodyInvalido.put("post_id", valorInvalido);
        bodyInvalido.put("name", "Tom");
        bodyInvalido.put("email", "email@prueba.com");
        bodyInvalido.put("body", "Contenido de prueba");

        RequestSpecification request = context.getRequest();
        Response response = request
                    .header("Content-Type", ContentType.JSON)
                    .body(bodyInvalido)
                .when()
                    .post(Endpoints.COMMENTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }


    @When("realizo una petición para consultar el comentario")
    public void consultarComentario() {
        Response response = context.getRequest()
                    .pathParam("commentId", context.getCommentId())
                .when()
                    .get(Endpoints.COMMENT)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para modificar el comentario")
    public void modificarComentario() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .pathParam("commentId", context.getCommentId())
                    .body(commentRequest)
                .when()
                    .put(Endpoints.COMMENT)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para eliminar el comentario")
    public void eliminarComentario() {
        Response response = context.getRequest()
                    .pathParam("commentId", context.getCommentId())
                .when()
                    .delete(Endpoints.COMMENT)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, false);
    }

    @When("realizo una petición para consultar los comentarios de la publicación")
    public void consultarComentariosPublicacion() {
        Response response = context.getRequest()
                    .pathParam("postId", context.getPostId())
                .when()
                    .get(Endpoints.POST_COMMENTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @When("realizo una petición para crear un comentario en la publicación")
    public void crearComentarioPublicacion() {
        Response response = context.getRequest()
                    .header("Content-Type", ContentType.JSON)
                    .pathParam("postId", context.getPostId())
                    .body(commentRequest)
                .when()
                    .post(Endpoints.POST_COMMENTS)
                .then()
                    .log().all()
                    .extract().response();

        context.setResponse(response);
        handleResponse(response, true);
    }

    @And("la lista de comentarios esta vacía")
    public void validarListaDeComentariosVacia(){
        assertNotNull(commentListResponse, AssertionsMessages.PARSING_ERROR);
        assertTrue(commentListResponse.isEmpty(), String.format(AssertionsMessages.EMPTY_LIST, "comentarios"));
    }

    @And("la lista de comentarios no esta vacía")
    public void validarListaDeComentariosNoVacia(){
        assertNotNull(commentListResponse, AssertionsMessages.PARSING_ERROR);
        assertFalse(commentListResponse.isEmpty(), String.format(AssertionsMessages.EMPTY_LIST, "comentarios"));
    }

    @And("todos los comentarios tienen los campos id, post_id, name, email y body")
    public void validarCamposComentarioEnListaDeComentarios(){
        commentListResponse.getCommentList().forEach(
                comment -> assertAll(
                        "Validando integridad del comentario con ID: " + comment.getId(),
                        () -> assertAll("Validación del campo id",
                                () -> assertNotNull(comment.getId(), String.format(AssertionsMessages.NULL_FIELD, "id")),
                                () -> assertTrue(comment.getId() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "id"))),
                        () -> assertAll("Validación del campo post_id",
                                () -> assertNotNull(comment.getPost_id(), String.format(AssertionsMessages.NULL_FIELD, "post_id")),
                                () -> assertTrue(comment.getPost_id() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "post_id"))),
                        () -> assertAll("Validación del campo name",
                                () -> assertNotNull(comment.getName(), String.format(AssertionsMessages.NULL_FIELD, "name")),
                                () -> assertFalse(comment.getName().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "name"))),
                        () -> assertAll("Validación del campo email",
                                () -> assertNotNull(comment.getEmail(), String.format(AssertionsMessages.NULL_FIELD, "email")),
                                () -> assertFalse(comment.getEmail().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "email"))),
                        () -> assertAll("Validación del campo body",
                                () -> assertNotNull(comment.getBody(), String.format(AssertionsMessages.NULL_FIELD, "body")),
                                () -> assertFalse(comment.getBody().isBlank(), String.format(AssertionsMessages.EMPTY_FIELD, "body")))
                )
        );
    }

    @And("la respuesta contiene el identificador del comentario")
    public void validarIdRespuesta(){
        assertNotNull(commentResponse, AssertionsMessages.PARSING_ERROR);
        assertNotNull(commentResponse.getId(), String.format(AssertionsMessages.NULL_FIELD, "id"));
        assertTrue(commentResponse.getId() > 0, String.format(AssertionsMessages.NON_POSITIVE_FIELD, "id"));
    }

    @And("la respuesta contiene los datos enviados en la petición al crear el comentario")
    @And("la respuesta contiene los datos enviados en la petición al modificar el comentario")
    public void validarRespuestaIncluyeCamposPeticion(){
        assertNotNull(commentResponse, AssertionsMessages.PARSING_ERROR);

        assertAll("Validando que la respuesta coincide con la petición",
                () -> assertEquals(commentRequest.getPost_id(), commentResponse.getPost_id(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "post_id")),
                () -> assertEquals(commentRequest.getName(), commentResponse.getName(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "name")),
                () -> assertEquals(commentRequest.getEmail(), commentResponse.getEmail(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "email")),
                () -> assertEquals(commentRequest.getBody(), commentResponse.getBody(),
                        String.format(AssertionsMessages.FIELD_MISMATCH, "body"))
        );
    }

    private void handleResponse(Response response, boolean expectList) {
        int statusCode = response.statusCode();
        switch (statusCode) {
            case HttpStatus.SC_OK -> {
                if(expectList){
                    commentListResponse = CommentListResponse.valueOf(response.as(new TypeRef<List<CommentResponse>>(){}));
                } else {
                    commentResponse = response.as(CommentResponse.class);
                }
            }
            case HttpStatus.SC_CREATED -> {
                commentResponse = response.as(CommentResponse.class);
                context.setCommentId(commentResponse.getId());
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
