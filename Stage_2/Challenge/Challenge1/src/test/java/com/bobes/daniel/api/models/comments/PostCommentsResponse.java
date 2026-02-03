package com.bobes.daniel.api.models.comments;

import java.util.List;

public class PostCommentsResponse {

    private final List<CommentResponse> commentResponses;

    public PostCommentsResponse(List<CommentResponse> comments){
        this.commentResponses = comments;
    }

    public List<CommentResponse> getComments() {
        return commentResponses;
    }

    public static PostCommentsResponse valueOf(List<CommentResponse> comments) {
        return new PostCommentsResponse(comments);
    }
}
