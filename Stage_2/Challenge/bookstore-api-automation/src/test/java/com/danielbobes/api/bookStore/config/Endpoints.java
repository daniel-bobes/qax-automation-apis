package com.danielbobes.api.bookStore.config;

public class Endpoints {
    private static final String ACCOUNT = "/Account/v1";
    public static final String USER = ACCOUNT + "/User";
    public static final String GENERAR_TOKEN = ACCOUNT + "/GenerateToken";
    public static final String GET_USER = USER + "/{userId}";

    private static final String BOOKSTORE = "/BookStore/v1";
    public static final String BOOKS = BOOKSTORE + "/Books";
    public static final String BOOK = BOOKSTORE + "/Book";

    private Endpoints() {}
}
