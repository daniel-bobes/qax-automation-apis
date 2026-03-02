package com.danielbobes.api.gorest.models.users;

import com.danielbobes.api.gorest.exceptions.MissingTestDataException;

import java.util.Arrays;
import java.util.List;

public class UserListResponse {

    private List<UserResponse> userList;

    public UserListResponse() {

    }

    public UserListResponse(List<UserResponse> userList) {
        this.userList = userList;
    }

    public List<UserResponse> getUserList() {
        return userList;
    }

    public void setUserList(List<UserResponse> userList) {
        this.userList = userList;
    }

    public boolean isEmpty(){
        return userList.isEmpty();
    }

    public String getOneUsedEmail(String emailToIgnore){
        return userList.stream()
                .map(UserResponse::getEmail)
                .filter(email -> !email.equalsIgnoreCase(emailToIgnore))
                .findFirst()
                .orElseThrow(MissingTestDataException::new);
    }

    public static UserListResponse valueOf(List<UserResponse> userList){
        return new UserListResponse(userList);
    }

    public static UserListResponse valueOf(UserResponse[] userList){
        return new UserListResponse(Arrays.asList(userList));
    }

}
