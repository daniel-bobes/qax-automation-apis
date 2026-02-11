package com.danielbobes.api.challenges.jsonplaceholder.models.comments;

public class CommentRequest {
    private Integer postId;
    private String name;
    private String email;
    private String body;

    public CommentRequest() {}

    public CommentRequest(Integer postId, String name, String email, String body) {
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Integer getPostId() {
        return postId;
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

    public static CommentRequest valueOf(Integer postId, String name, String email, String body) {
        return new CommentRequest(postId, name, email, body);
    }
}
