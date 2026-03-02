package com.danielbobes.api.gorest.context;

import com.danielbobes.api.gorest.config.Config;
import com.danielbobes.api.gorest.config.enums.AuthType;
import com.danielbobes.api.gorest.models.common.ErrorListResponse;
import com.danielbobes.api.gorest.models.common.MessageError;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Objects;
import java.util.Optional;

import static com.danielbobes.api.gorest.config.specs.RequestSpecs.*;

public class TestContext {

    private AuthType authType;
    private String userId;
    private String lastUsedEmail;
    private Integer postId;
    private Integer commentId;
    private Response response;
    private MessageError messageError;
    private ErrorListResponse errorListResponse;

    public String getUserId() {
        return userId;
    }

    public String getLastUsedEmail() {
        return lastUsedEmail;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentId(){
        return commentId;
    }

    public Response getResponse() {
        return response;
    }

    public MessageError getMessageError() {
        return messageError;
    }

    public ErrorListResponse getErrorListResponse() {
        return errorListResponse;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastUsedEmail(String lastUsedEmail) {
        this.lastUsedEmail = lastUsedEmail;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setMessageError(MessageError messageError) {
        this.messageError = messageError;
    }

    public void setErrorListResponse(ErrorListResponse errorListResponse) {
        this.errorListResponse = errorListResponse;
    }

    public void setTokenType(AuthType authType) {
        this.authType = authType;
    }

    public boolean hasUserId(){
        return !Objects.isNull(userId);
    }

    public RequestSpecification getRequest() {
        var authTypeChosen = Optional.ofNullable(authType).orElse(AuthType.VALID);
        authType = null;
        return switch (authTypeChosen) {
            case ABSENT -> noAuthSpec();
            case INVALID -> baseSpec(Config.INVALID_TOKEN);
            case VALID -> baseSpec();
        };
    }

}
