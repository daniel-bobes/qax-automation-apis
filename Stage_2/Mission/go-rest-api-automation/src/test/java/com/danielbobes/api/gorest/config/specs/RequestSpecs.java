package com.danielbobes.api.gorest.config.specs;

import com.danielbobes.api.gorest.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class RequestSpecs {

    private RequestSpecs() {

    }

    private static RequestSpecBuilder getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .setBasePath(Config.BASE_PATH)
                .log(LogDetail.ALL);
    }

    private static RequestSpecification getUnauthenticatedRequestSpec() {
        return getBaseRequestSpec().build();
    }

    private static RequestSpecification getAuthenticatedRequestSpec(String token) {
        return getBaseRequestSpec()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static RequestSpecification baseSpec() {
        return given().spec(getAuthenticatedRequestSpec(Config.AUTH_TOKEN));
    }

    public static RequestSpecification baseSpec(String token){
        return given().spec(getAuthenticatedRequestSpec(token));
    }

    public static RequestSpecification noAuthSpec(){
        return given().spec(getUnauthenticatedRequestSpec());
    }
}
