package com.danielbobes.api.gorest.steps;

import com.danielbobes.api.gorest.config.Endpoints;
import com.danielbobes.api.gorest.config.specs.RequestSpecs;
import io.cucumber.java.BeforeAll;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assumptions;

import java.util.stream.Stream;

public class Hooks {

    @BeforeAll
    public static void checkApiHealth() {
        boolean isAllGood = Stream.of(Endpoints.USERS, Endpoints.POSTS, Endpoints.COMMENTS)
                .allMatch(endpoint -> RequestSpecs.baseSpec()
                        .get(endpoint).statusCode() == HttpStatus.SC_OK);
        Assumptions.assumeTrue(isAllGood,"La API está caída");
    }

}
