package com.danielbobes.api.challenges.jsonplaceholder.steps.comments;

import java.util.List;
import java.util.Map;


import com.danielbobes.api.challenges.jsonplaceholder.config.Config;
import com.danielbobes.api.challenges.jsonplaceholder.config.Endpoints;
import com.danielbobes.api.challenges.jsonplaceholder.models.comments.CommentRequest;
import com.danielbobes.api.challenges.jsonplaceholder.models.comments.CommentResponse;
import com.danielbobes.api.challenges.jsonplaceholder.models.comments.PostCommentsResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

public class CommentsSteps {

    private CommentRequest commentRequestBody;
    private CommentResponse commentResponseBody;
    private Response response;
    private PostCommentsResponse postCommentsResponseBody;

    @Given("que el usuario quiere agregar un comentario a un post existente")
    public void queElUsuarioCreaUnComentarioAUnPostExistente() {
        // Paso descriptivo: define el contexto de la prueba
    }

    @And("el usuario proporciona la siguiente información:")
    public void elUsuarioProporcionaLaSiguienteInformation(DataTable table) {
        // Convertimos la tabla en una lista de mapas con clave String y valores de tipo String
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        // Obtenemos el primer comentario
        Map<String, String> firstComment = rows.get(0);

        // Construimos el comentario y lo guardamos
        commentRequestBody = CommentRequest.valueOf(
                Integer.valueOf(firstComment.get("postId")),
                firstComment.get("nombre"),
                firstComment.get("email"),
                firstComment.get("comentario")
        );
    }

    @When("el usuario envía la solicitud para crear el comentario")
    public void elUsuarioEnviaLaSolicitudParaCrearElComentario() {
        response =
                given()
                        .log().all()
                        .baseUri(Config.BASE_URL)
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .pathParam("postId", commentRequestBody.getPostId())
                        .body(commentRequestBody)
                .when()
                        .post(Endpoints.CREATE_COMMENT)
                .then()
                        .log().all()
                        .extract().response();

        commentResponseBody = response.body().as(CommentResponse.class);
    }

    @Then("el sistema debe registrar el comentario correctamente")
    public void elSistemaDebeRegistrarElComentarioCorrectamente() {
        // Paso descriptivo: define el contexto de la prueba
    }

    @And("debe responder con un código {int}")
    public void debeResponderConUnCodigo(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @And("el comentario registrado debe incluir un ID generado por el sistema")
    public void validarIdComentarioCreado(){
        assertNotNull(commentResponseBody, "Debe parsearse la respuesta");
        assertNotNull(commentResponseBody.getId(), "El ID del comentario no debe ser nulo");
        assertTrue(commentResponseBody.getId() > 0, "El ID del comentario debe ser positivo");
    }

    @And("los datos enviados deben coincidir con la información registrada")
    public void validarQueLosDatosEnviadosCoincidenConLosDatosRecibidos(){
        assertNotNull(commentResponseBody, "Debe parsearse la respuesta");
        assertEquals(commentRequestBody.getPostId(), commentResponseBody.getPostId(), "Los IDs del post del comentario deben coincidir");
        assertEquals(commentRequestBody.getName(), commentResponseBody.getName(), "Los nombres del comentario deben coincidir");
        assertEquals(commentRequestBody.getEmail(), commentResponseBody.getEmail(), "Los emails del comentario deben coincidir");
        assertEquals(commentRequestBody.getBody(), commentResponseBody.getBody(), "Los cuerpos de los comentarios deben coincidir");
    }

    @Given("que el usuario quiere ver los comentarios asociados a un post específico")
    public void queElUsuarioQuiereVerLosComentariosAsociadosAUnPostEspecifico(){
        // Paso descriptivo: define el contexto de la prueba
    }

    @And("el post tiene comentarios registrados")
    public void elPostTieneComentariosRegistrados(){
        // Paso descriptivo: define el contexto de la prueba
    }

    @When("el usuario solicita ver los comentarios del post con ID {int}")
    public void consultarComentariosDelPost(Integer postId){
        response =
                given()
                        .log().all()
                        .baseUri(Config.BASE_URL)
                        .queryParam("postId", postId)
                .when()
                        .get(Endpoints.COMMENTS)
                .then()
                        .log().all()
                        .extract().response();

        postCommentsResponseBody = PostCommentsResponse.valueOf(response.as(new TypeRef<List<CommentResponse>>() {}));
    }

    @Then("el sistema debe devolver la lista de comentarios existentes")
    public void validarQueDevuelveListaDeComentariosExistentes() {
        assertNotNull(postCommentsResponseBody.getComments(),
                "Debe parsearse la respuesta");
        assertFalse(postCommentsResponseBody.getComments().isEmpty(),
                "La lista de comentarios existentes no debe estar vacía");
    }

    @And("cada comentario debe incluir un ID, nombre, email válido y contenido")
    public void validarCamposObligatoriosComentario(){
        assertNotNull(postCommentsResponseBody.getComments(),
                "Debe parsearse la respuesta");
        assertFalse(postCommentsResponseBody.getComments().isEmpty(),
                "La lista de comentarios existentes no debe estar vacía");

        postCommentsResponseBody.getComments().forEach(
            comment -> {
                // Con assert all si falla una validaciones en un comentario no se para y evalua el resto
                // de validaciones para el mismo comentario
                // Si un comentario no pasa las validaciones, se detiene
                assertAll("Fallo en las validaciones del comentario " + comment.getId(),
                        () -> assertNotNull(comment.getPostId(), "El comentario debe tener el ID del post al que pertenece"),
                        () -> assertNotNull(comment.getId(), "El comentario debe tener un ID"),
                        () -> assertNotNull(comment.getName(), "El comentario debe tener un nombre"),
                        () -> assertNotNull(comment.getEmail(), "El comentario debe tener un email"),
                        () -> assertNotNull(comment.getBody(), "El comentario debe tener un cuerpo"));
            });
    }

    // Las expresiones de cucumber van en minúsculas, por eso string
    // Cuando buscamos un string, cucumber da por hecho que va entre comillas dobles
    @And("el email de cada comentario debe contener {string}")
    public void validarEmailDeCadaComentario(String caracterEsperado){
        postCommentsResponseBody.getComments().forEach(
                comment -> {
                    assertAll("Fallo en las validaciones del email del comentario " + comment.getId(),
                            () -> assertNotNull(comment.getEmail(),
                                    "El comentario debe tener un email"),
                            () -> assertTrue(comment.getEmail().contains(caracterEsperado),
                                    "El email debe contener la cadena de caracteres esperada"));
                });
    }

}
