package com.danielbobes.api.gorest.models.users;

public class UserResponse extends UserRequest {

    private String id;

    public UserResponse() {
        
    }

    public UserResponse(String id, String name, String gender, String email, String status) {
        super(name, gender, email, status);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
