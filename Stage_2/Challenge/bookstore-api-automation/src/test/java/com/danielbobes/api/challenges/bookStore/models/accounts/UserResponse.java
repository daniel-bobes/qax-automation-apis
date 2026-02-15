package com.danielbobes.api.challenges.bookStore.models.accounts;

import com.danielbobes.api.challenges.bookStore.models.books.Book;

import java.util.List;

public class UserResponse {

    private String userId;
    private String username;
    private List<Book> books;

    public String getUserId() {
        return userId;
    }

    public void setUserID(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
