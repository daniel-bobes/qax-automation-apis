package com.danielbobes.api.gorest.models.comments;

public class CommentResponse extends CommentRequest {

    private Integer id;

    public CommentResponse() {

    }

    public CommentResponse(Integer id, Integer post_id, String name, String email, String body) {
        super(post_id, name, email, body);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
