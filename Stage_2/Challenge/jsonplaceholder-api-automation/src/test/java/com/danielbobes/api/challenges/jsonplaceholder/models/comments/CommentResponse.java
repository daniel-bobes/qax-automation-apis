package com.danielbobes.api.challenges.jsonplaceholder.models.comments;

public class CommentResponse {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

    public Integer getPostId() {
        return postId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
