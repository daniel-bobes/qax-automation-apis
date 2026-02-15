package com.danielbobes.api.challenges.bookStore.steps.books;

import com.danielbobes.api.challenges.bookStore.config.Endpoints;
import com.danielbobes.api.challenges.bookStore.models.books.AddBookRequest;
import com.danielbobes.api.challenges.bookStore.models.books.AddBookResponse;
import com.danielbobes.api.challenges.bookStore.models.books.BooksResponse;
import com.danielbobes.api.challenges.bookStore.models.books.ISBN;
import com.danielbobes.api.challenges.bookStore.utils.RunContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.danielbobes.api.challenges.bookStore.config.RequestSpecifications.*;

public class BookSteps {

    private String isbn;

    private Response response;

    private BooksResponse booksResponse;
    private AddBookResponse addBookResponse;

    @Given("que el usuario consulta los libros disponibles en la tienda")
    public void queElUsuarioConsultaLosLibrosDisponibles() {
        consultarLibrosDisponiblesEnLaTienda();
    }

    @When("realiza una petición GET para consultar los libros disponibles en la tienda")
    public void consultarLibrosDisponiblesEnLaTienda() {
        response = baseSpec()
                .when()
                    .get(Endpoints.BOOKS)
                .then()
                    .log().all()
                    .extract().response();

        if (response.statusCode() == HttpStatus.SC_OK) {
            booksResponse = response.as(BooksResponse.class);
            if(!booksResponse.getBooks().isEmpty()) {
                isbn = booksResponse.getBooks().get(0).getIsbn();
                RunContext.setIsbn(isbn);
            }
        }
    }

    @When("realiza una petición POST para añadir el primer libro a su colección")
    public void anadirLibroAlUsuario(){
        AddBookRequest addBookRequest = new AddBookRequest(RunContext.getUserId(), List.of(new ISBN(isbn)));

        response = authSpec(RunContext.getToken())
                        .body(addBookRequest)
                        .header("Content-Type", ContentType.JSON)
                    .when()
                        .post(Endpoints.BOOKS)
                    .then()
                        .log().all()
                        .extract().response();

        if (response.statusCode() == HttpStatus.SC_CREATED) {
            addBookResponse = response.as(AddBookResponse.class);
            RunContext.setResponse(response);
        }

    }

    @And("la lista de libros no debe estar vacía")
    public void laListaDeLibrosNoDebeEstarVacia() {
        assertNotNull(booksResponse, "Debe setearse la respuesta");
        assertFalse(booksResponse.getBooks().isEmpty(), "La lista no debe estar vacía");
    }

    @And("el libro debe aparecer en la colección del usuario")
    public void elLibroDebeAparecer(){
        assertNotNull(addBookResponse, "Debe parsearse la respuesta");
        assertTrue(addBookResponse.getBooks().contains(new ISBN(isbn)));
    }
}
