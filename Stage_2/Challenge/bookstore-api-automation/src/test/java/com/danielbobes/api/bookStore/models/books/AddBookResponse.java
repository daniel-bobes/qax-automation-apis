package com.danielbobes.api.bookStore.models.books;

import java.util.List;

public class AddBookResponse {

    private String userId;
    private List<ISBN> books;

    public AddBookResponse() {
    }

    public AddBookResponse(String userId, List<ISBN> books) {
        this.userId = userId;
        this.books = books;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ISBN> getBooks() {
        return books;
    }

    public void setBooks(List<ISBN> books) {
        this.books = books;
    }

}
