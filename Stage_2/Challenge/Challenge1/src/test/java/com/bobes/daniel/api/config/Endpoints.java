package com.bobes.daniel.api.config;

public class Endpoints {
    public static final String POSTS = "/posts";
    public static final String COMMENTS = "/comments";
    public static final String CREATE_COMMENT = POSTS + "/{postId}" + Endpoints.COMMENTS;

    private Endpoints() {}
}
