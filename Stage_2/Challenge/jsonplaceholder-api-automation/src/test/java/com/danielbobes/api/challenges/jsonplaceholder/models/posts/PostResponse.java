package com.danielbobes.api.challenges.jsonplaceholder.models.posts;

public class PostResponse {
    private Integer id;
    private String title;
    private String body;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getUserId() {
        return userId;
    }

}
