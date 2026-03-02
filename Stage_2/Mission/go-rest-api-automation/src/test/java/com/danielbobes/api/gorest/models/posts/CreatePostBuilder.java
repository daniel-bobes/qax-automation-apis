package com.danielbobes.api.gorest.models.posts;

import com.danielbobes.api.gorest.models.users.CreateUserBuilder;

public final class CreatePostBuilder {

    private final PostRequest postRequest;

    private CreatePostBuilder() {
        postRequest = new PostRequest();
    }

    public static CreatePostBuilder aPost() {
        return new CreatePostBuilder();
    }

    public CreatePostBuilder withUserId(Integer userId) {
        postRequest.setUser_id(userId);
        return this;
    }

    public CreatePostBuilder withTitle(String title) {
        postRequest.setTitle(title);
        return this;
    }

    public CreatePostBuilder withBody(String body) {
        postRequest.setBody(body);
        return this;
    }

    public PostRequest build() {
        return postRequest;
    }

}
