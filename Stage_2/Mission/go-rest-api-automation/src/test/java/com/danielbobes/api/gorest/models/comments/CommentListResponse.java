package com.danielbobes.api.gorest.models.comments;

import java.util.Arrays;
import java.util.List;

public class CommentListResponse {

    private List<CommentResponse> commentList;

    public CommentListResponse() {

    }

    public CommentListResponse(List<CommentResponse> commentList) {
        this.commentList = commentList;
    }

    public List<CommentResponse> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentResponse> commentList) {
        this.commentList = commentList;
    }

    public boolean isEmpty() {
        return commentList.isEmpty();
    }

    public static CommentListResponse valueOf(List<CommentResponse> commentList){
        return new CommentListResponse(commentList);
    }

    public static CommentListResponse valueOf(CommentResponse[] commentList){
        return new CommentListResponse(Arrays.asList(commentList));
    }
}
