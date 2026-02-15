package com.danielbobes.api.bookStore.config;

import com.danielbobes.api.bookStore.utils.RunContext;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecifications {

    private RequestSpecifications() {

    }

    public static RequestSpecification baseSpec() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        return RestAssured.
                given().
                    log().all()
                    .baseUri(Config.BASE_URL);
    }

    public static RequestSpecification authSpec(String token) {
        return baseSpec()
                .header("Authorization", "Bearer " + token);
    }

    public static RequestSpecification authSpec() {
        return authSpec(RunContext.getToken());
    }

}
