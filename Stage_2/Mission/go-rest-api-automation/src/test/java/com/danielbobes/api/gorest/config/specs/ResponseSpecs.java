package com.danielbobes.api.gorest.config.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecs {

    private ResponseSpecs() {

    }

    public static ResponseSpecification getBaseRequestSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
