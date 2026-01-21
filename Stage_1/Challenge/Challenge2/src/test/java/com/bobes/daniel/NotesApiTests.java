package com.bobes.daniel;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NotesApiTests {

    private static final String baseURL = "https://practice.expandtesting.com/notes/api";

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = baseURL;
    }

    @Test
    @DisplayName("CP01 - Registrar usuario correctamente")
    public void registrarUsuario(){
        String name = "Prueba", emailNuevo = "prueba_%s@test.com", password = "prueba123";

        emailNuevo = String.format(emailNuevo,
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        given().
                log().all().
                contentType("application/x-www-form-urlencoded; charset=utf-8").
                formParam("name", name).
                formParam("email", emailNuevo).
                formParam("password", password).
        when().
                post("/users/register").
        then().
                log().all().
                statusCode(201).
                body("success", equalTo(true)).
                body("message", equalTo("User account created successfully")).
                body("data.id", notNullValue()).
                body("data.name", equalTo(name)).
                body("data.email", equalTo(emailNuevo));
    }

    @Test
    @DisplayName("CP02 - Login de usuario correctamente")
    public void loginUsuario(){
        String email = "prueba@test.com", password = "prueba123";

        given().
                log().all().
                contentType("application/x-www-form-urlencoded; charset=utf-8").
                formParam("email", email).
                formParam("password", password).
        when().
                post("/users/login").
        then().
                log().all().
                statusCode(200).
                body("success", equalTo(true)).
                body("message", equalTo("Login successful")).
                body("data.token", notNullValue()).
                body("data.name", notNullValue()).
                body("data.email", notNullValue()).
                body("data.email", equalTo(email));
    }

    @Test
    @DisplayName("CP03 - Registrar un usuario ya registrado")
    public void registrarUsuarioExistente(){
        String name = "Otra prueba", emailRegistrado = "prueba@test.com", password = "123prueba";

        given().
                log().all().
                contentType("application/x-www-form-urlencoded; charset=utf-8").
                formParam("name", name).
                formParam("email", emailRegistrado).
                formParam("password", password).
        when().
                post("/users/register").
        then().
                log().all().
                statusCode(409).
                body("success", notNullValue()).
                body("status", notNullValue()).
                body("message", notNullValue()).
                body("success", equalTo(false)).
                body("status", equalTo(409)).
                body("message", equalTo("An account already exists with the same email address"));
    }

    @Test
    @DisplayName("CP04 - Login de usuario incorrecto")
    public void loginUsuarioIncorrecto(){
        String email = "prueba@test.com", password = "123prueba";

        given().
                log().all().
                contentType("application/x-www-form-urlencoded; charset=utf-8").
                formParam("email", email).
                formParam("password", password).
        when().
                post("/users/login").
        then().
                log().all().
                statusCode(401).
                body("success", notNullValue()).
                body("status", notNullValue()).
                body("message", notNullValue()).
                body("success", equalTo(false)).
                body("status", equalTo(401)).
                body("message", equalTo("Incorrect email address or password"));
    }
}
