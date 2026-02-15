package com.danielbobes.api.challenges.bookStore.steps;

import com.danielbobes.api.challenges.bookStore.utils.RunContext;
import io.cucumber.java.Before;

public class Hooks {

    @Before("@NewContext")
    public void prepareContext() {
        // Esto limpia la memoria estática antes de cada escenario
        // garantizando que no haya colisión de datos.
        RunContext.reset();
    }
}
