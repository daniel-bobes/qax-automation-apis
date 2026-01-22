package com.bobes.daniel;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class TheSimpsonsApiTests {

    private static final String baseURL = "https://thesimpsonsapi.com";

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = baseURL;
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("CP01 - Consultar listado de personajes paginado, validar campos obligatorios")
    public void validarCamposObligatoriosListadoPersonajes(){
        given().
                log().all().
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("$", hasKey("count")).
                body("$", hasKey("next")).
                body("$", hasKey("prev")).
                body("$", hasKey("pages")).
                body("$", hasKey("results"));
    }

    @Test
    @DisplayName("CP02 - Consultar listado de personajes paginado, validar tipo de contenido de la respuesta")
    public void validarTipoContendidoListadoPersonajes(){
        given().
                log().all().
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                contentType("application/json; charset=utf-8");
    }

    @Test
    @DisplayName("CP03 - Consultar listado de personajes paginado, validar estructura minima de cada personaje del listado")
    public void validarEstructuraMinimaPersonaListadoPersonajes(){
        given().
                log().all().
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("results", not(empty())).
                body("results.every { it.containsKey('id') }",  is(true)).
                body("results.every { it.containsKey('age') }",  is(true)).
                body("results.every { it.containsKey('birthdate') }",  is(true)).
                body("results.every { it.containsKey('gender') }",  is(true)).
                body("results.every { it.containsKey('name') }",  is(true)).
                body("results.every { it.containsKey('occupation') }",  is(true)).
                body("results.every { it.containsKey('portrait_path') }",  is(true)).
                body("results.every { it.containsKey('phrases') }",  is(true)).
                body("results.every { it.containsKey('status') }",  is(true));
    }

    @Test
    @DisplayName("CP04 - Consultar listado de personajes paginado, validar primera página sin indicar un numéro de página")
    public void validarPrimeraPaginaSinIndicadorDePaginaListadoPersonajes(){
        given().
                log().all().
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", nullValue()).
                body("next", notNullValue()).
                body("next", containsString("page=2"));
    }

    @Test
    @DisplayName("CP05 - Consultar listado de personajes paginado, consultar primera página")
    public void validarPrimeraPaginaConIndicadorDePaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 1).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", nullValue()).
                body("next", notNullValue()).
                body("next", containsString("page=2"));
    }

    @Test
    @DisplayName("CP06 - Consultar listado de personajes paginado, consultar segunda página")
    public void validarSegundaPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 2).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", allOf(notNullValue(), containsString("page=1"))).
                body("next", allOf(notNullValue(), containsString("page=3")));
    }

    @Test
    @DisplayName("CP07 - Consultar listado de personajes paginado, consultar penúltima página")
    public void validarPenultimaPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 59).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", allOf(notNullValue(), containsString("page=58"))).
                body("next", allOf(notNullValue(), containsString("page=60")));
    }

    @Test
    @DisplayName("CP08 - Consultar listado de personajes paginado, consultar última página")
    public void validarUltimaPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 60).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", allOf(notNullValue(), containsString("page=59"))).
                body("next", nullValue());
    }

    @Test
    @DisplayName("CP09 - Consultar listado de personajes paginado, consultar página mayor a la última página")
    public void validarPaginaMayorUltimaPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 61).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("prev", nullValue()).
                body("next", nullValue()).
                body("results", empty());
    }

    @Test
    @DisplayName("CP10 - Consultar listado de personajes paginado, validar cálculo de metadatos")
    public void validarCalculoMetadatosListadoPersonajes(){
        given().
                log().all().
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("count", allOf(notNullValue(), equalTo(1182))).
                body("results.size()", lessThanOrEqualTo(20));
    }

    @Test
    @DisplayName("CP11 - Consultar listado de personajes paginado, validar orden")
    public void validarOrdenAscendenteListadoPersonajes(){
        List<Integer> ids = given().
                        log().all().
                when().
                        get("/characters").
                then().
                        log().all().
                        statusCode(HttpStatus.SC_OK).
                        extract().
                            path("results.id");

        List<Integer> idsOrdenados = new ArrayList<>(ids);
        Collections.sort(idsOrdenados);

        assertEquals(idsOrdenados, ids, "La lista no está ordenada por ID de forma ascendente");
    }

    @Test
    @DisplayName("CP12 - Consultar detalle de un personaje correctamente, validar campos obligatorios")
    public void validarEstructuraMinimaDetallePersonaje(){
        given().
                log().all().
                pathParam("id", 3).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("$", hasKey("id")).
                body("$", hasKey("age")).
                body("$", hasKey("birthdate")).
                body("$", hasKey("gender")).
                body("$", hasKey("name")).
                body("$", hasKey("occupation")).
                body("$", hasKey("portrait_path")).
                body("$", hasKey("phrases")).
                body("$", hasKey("status"));
    }

    @Test
    @DisplayName("CP13 - Consultar detalle de un personaje correctamente, el primer personaje debe ser Homer Simpson")
    public void validarPrimerPersonajeDetallePersonaje(){
        given().
                log().all().
                pathParam("id", 1).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("name", equalTo("Homer Simpson"));
    }

    @Test
    @DisplayName("CP14 - Consultar detalle de un personaje, validar formato de la fecha de nacimiento")
    public void validarFormatoFechaNacimientoDetallePersonaje(){
        given().
                log().all().
                pathParam("id", 1).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("birthdate", matchesRegex("^\\d{4}-\\d{2}-\\d{2}$"));
    }

    @Test
    @DisplayName("CP15 - Consultar detalle de un personaje, validar formato de la ruta del retrato")
    public void validarFormatoRutaRetratoCampoPersonaje(){
        given().
                log().all().
                pathParam("id", 1).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("portrait_path", matchesPattern("^/character/\\d+.webp$"));
    }

    @Test
    @DisplayName("CP16 - Consultar detalle de un personaje, validar formato de la lista de frases")
    public void validarFormatoListaDeFrasesDetallePersonaje(){
        given().
                log().all().
                pathParam("id", 3).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("phrases", allOf(not(empty()), instanceOf(List.class)));
    }

    @Test
    @DisplayName("CP17 - Consultar listado de personajes con paginación indicando un valor de página no entero")
    public void validarFormatoPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", "test").
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("$", hasKey("error")).
                body("error", equalTo("Invalid page parameter"));
    }

    @Test
    @DisplayName("CP18 - Consultar listado de personajes con paginación indicando un valor de página entero menor a 1")
    public void validarLimiteInferiorPaginaListadoPersonajes(){
        given().
                log().all().
                param("page", 0).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("$", hasKey("error")).
                body("error", equalTo("Invalid page parameter"));
    }

    @Test
    @DisplayName("CP19 - Consultar detalle de un personaje inexistente, identificador numérico")
    public void validarIdPersonajeNoExistenteDetallePersonaje(){
        int idPersonajeNoExistente = 9999;
        given().
                log().all().
                pathParam("id", idPersonajeNoExistente).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body("$", allOf(hasKey("error"), hasKey("id"))).
                body("error", equalTo("Character not found")).
                body("id", equalTo(idPersonajeNoExistente));
    }

    @Test
    @DisplayName("CP20 - Consultar detalle de un personaje inexistente, identificador no numérico")
    public void validarIdPersonajeNoNumericoDetallePersonaje(){
        String idPersonajeNoExistente = "test";
        given().
                log().all().
                pathParam("id", idPersonajeNoExistente).
        when().
                get("/characters/{id}").
        then().
                log().all().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("$", allOf(hasKey("error"), hasKey("id"))).
                body("error", equalTo("Character not found")).
                body("id", equalTo(idPersonajeNoExistente));
    }
}
