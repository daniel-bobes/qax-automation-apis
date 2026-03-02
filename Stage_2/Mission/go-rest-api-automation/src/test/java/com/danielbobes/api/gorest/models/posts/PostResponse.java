package com.danielbobes.api.gorest.models.posts;

public class PostResponse extends PostRequest {

    private Integer id;

    public PostResponse() {

    }

    public PostResponse(Integer id, Integer user_id, String title, String body) {
        super(user_id, title, body);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
