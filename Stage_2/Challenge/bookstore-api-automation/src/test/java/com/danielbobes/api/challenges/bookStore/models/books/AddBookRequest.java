package com.danielbobes.api.challenges.bookStore.models.books;

import java.util.List;

public class AddBookRequest {

    private String userId;
    private List<ISBN> collectionOfIsbns;

    public AddBookRequest() {
    }

    public AddBookRequest(String userId, List<ISBN> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ISBN> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<ISBN> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }

}
