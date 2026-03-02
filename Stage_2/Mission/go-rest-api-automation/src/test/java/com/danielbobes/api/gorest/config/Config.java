package com.danielbobes.api.gorest.config;

public final class Config {

    public static final String BASE_URL = "https://gorest.co.in";
    public static final String BASE_PATH = "/public/v2";
    public static final String AUTH_TOKEN = System.getenv("GO_REST_API_AUTH_TOKEN");
    public static final String INVALID_TOKEN = "invalid_token_format_test";

    private Config() {

    }
}
