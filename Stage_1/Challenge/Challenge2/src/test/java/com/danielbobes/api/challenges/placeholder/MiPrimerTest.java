package com.danielbobes.api.challenges.placeholder;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MiPrimerTest {

    private static final String baseURL = "https://jsonplaceholder.typicode.com";

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = baseURL;
    }

    @Test
    @DisplayName("CP01 - Crear un post exitosamente")
    public void pruebaCrearPost() {
        String postTitle = "Prueba", postBody = "Contenido de prueba";
        int userId = 1;

        given().
                log().all().
                contentType("application/json").
        when().
                body("{\n" +
                        "\"title\": \"" + postTitle + "\",\n" +
                        "\"body\": \"" + postBody + "\",\n" +
                        "\"userId\": 1\n" +
                        "}").
                post("/posts").
        then().
                log().all().
                statusCode(201).
                body("id", notNullValue()).
                body("title", equalTo(postTitle)).
                body("body", equalTo(postBody)).
                body("userId", equalTo(userId));
    }

    @Test
    @DisplayName("CP02 - Listar usuarios correctamente")
    public void listarUsuarios() {
        given().
                log().all().
        when().
                get("/users").
        then().
                log().all().
                statusCode(200).
                body("$", not(empty())).
                body("id", everyItem(notNullValue())).
                body("name", everyItem(notNullValue())).
                body("username", everyItem(notNullValue())).
                body("email", everyItem(notNullValue()));
    }

    @Test
    @DisplayName("CP03 - Obtener comentarios asociados a un post")
    public void listarComentariosPorPost() {
        given().
                log().all().
        when().
                get("/comments?postId=1").
        then().
                log().all().
                statusCode(200).
                body("$", not(empty())).
                body("id", everyItem(notNullValue())).
                body("name", everyItem(notNullValue())).
                body("email", everyItem(notNullValue())).
                body("body", everyItem(notNullValue()));
    }

    @Test
    @DisplayName("CP04 - Consultar comentarios de un post inexistente")
    public void listarComentariosPostInexistente() {
        given().
                log().all().
        when().
                get("/comments?postId=9999").
        then().
                log().all().
                statusCode(200).
                body("$", empty());
    }
}
