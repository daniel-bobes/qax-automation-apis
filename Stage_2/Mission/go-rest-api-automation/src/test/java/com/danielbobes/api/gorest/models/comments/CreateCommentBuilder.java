package com.danielbobes.api.gorest.models.comments;


public final class CreateCommentBuilder {

    private final CommentRequest commentRequest;

    private CreateCommentBuilder() {
        commentRequest = new CommentRequest();
    }

    public static CreateCommentBuilder aComment() {
        return new CreateCommentBuilder();
    }

    public CreateCommentBuilder withPostId(Integer postId) {
        commentRequest.setPost_id(postId);
        return this;
    }

    public CreateCommentBuilder withName(String name) {
        commentRequest.setName(name);
        return this;
    }

    public CreateCommentBuilder withEmail(String email) {
        commentRequest.setEmail(email);
        return this;
    }

    public CreateCommentBuilder withBody(String body) {
        commentRequest.setBody(body);
        return this;
    }

    public CommentRequest build() {
        return commentRequest;
    }
}
