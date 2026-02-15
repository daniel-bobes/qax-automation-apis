package com.danielbobes.api.challenges.bookStore.models.books;

import java.util.Objects;

public class ISBN {
    private String isbn;

    public ISBN() {

    }

    public ISBN(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN otherIsbn = (ISBN) o;
        return Objects.equals(isbn, otherIsbn.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
