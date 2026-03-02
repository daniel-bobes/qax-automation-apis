package com.danielbobes.api.gorest.models.comments;

public class CommentRequest {

    private Integer post_id;
    private String name;
    private String email;
    private String body;

    public CommentRequest() {

    }

    public CommentRequest(Integer post_id, String name, String email, String body) {
        this.post_id = post_id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
