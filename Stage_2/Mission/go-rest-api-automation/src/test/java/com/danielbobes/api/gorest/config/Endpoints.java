package com.danielbobes.api.gorest.config;

public final class Endpoints {

    public static final String USERS = "/users";
    public static final String USER = USERS + "/{userId}";
    public static final String POSTS = "/posts";
    public static final String POST = POSTS + "/{postId}";
    public static final String COMMENTS = "/comments";
    public static final String COMMENT = COMMENTS + "/{commentId}";

    public static final String USER_POSTS = USER + POSTS;
    public static final String POST_COMMENTS = POST + COMMENTS;

    private Endpoints() {

    }
}
