package com.danielbobes.api.gorest.models.users;

import com.danielbobes.api.gorest.config.enums.Gender;
import com.danielbobes.api.gorest.config.enums.Status;

public final class CreateUserBuilder {

    private final UserRequest userRequest;

    private CreateUserBuilder() {
        userRequest = new UserRequest();
    }

    public static CreateUserBuilder aUser(){
        return new CreateUserBuilder();
    }

    public CreateUserBuilder withName(String name) {
        userRequest.setName(name);
        return this;
    }

    public CreateUserBuilder withEmail(String email) {
        userRequest.setEmail(email);
        return this;
    }

    public CreateUserBuilder withGender(String gender) {
        userRequest.setGender(gender);
        return this;
    }

    public CreateUserBuilder withGender(Gender gender) {
        userRequest.setGender(gender.getValue());
        return this;
    }

    public CreateUserBuilder withStatus(String status) {
        userRequest.setStatus(status);
        return this;
    }

    public CreateUserBuilder withStatus(Status status) {
        userRequest.setStatus(status.getValue());
        return this;
    }

    public UserRequest build() {
        return userRequest;
    }
}
