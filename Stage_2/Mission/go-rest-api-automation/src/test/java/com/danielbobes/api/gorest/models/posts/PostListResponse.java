package com.danielbobes.api.gorest.models.posts;

import java.util.Arrays;
import java.util.List;

public class PostListResponse {

    private List<PostResponse> postList;

    public PostListResponse() {

    }

    public PostListResponse(List<PostResponse> postList) {
        this.postList = postList;
    }

    public List<PostResponse> getPostList() {
        return postList;
    }

    public void setPostList(List<PostResponse> postList) {
        this.postList = postList;
    }

    public boolean isEmpty() {
        return postList.isEmpty();
    }

    public static PostListResponse valueOf(List<PostResponse> postList){
        return new PostListResponse(postList);
    }

    public static PostListResponse valueOf(PostResponse[] postList){
        return new PostListResponse(Arrays.asList(postList));
    }

}
