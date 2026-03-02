package com.danielbobes.api.gorest.data.factory;

import com.danielbobes.api.gorest.config.enums.Gender;
import com.danielbobes.api.gorest.config.enums.Status;
import com.danielbobes.api.gorest.models.users.CreateUserBuilder;
import com.danielbobes.api.gorest.models.users.UserRequest;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;
import java.util.Locale;

public final class UserFactory {

    private static final Faker faker = new Faker(Locale.forLanguageTag("es"));

    private UserFactory() {

    }

    public static UserRequest createValidUser(){
        return CreateUserBuilder.aUser()
                .withName(faker.name().name())
                .withEmail(faker.internet().emailAddress())
                .withGender(faker.options().option(Gender.class))
                .withStatus(faker.options().option(Status.class))
                .build();
    }

    public static UserRequest createUserWithInvalidGender(){
        return CreateUserBuilder.aUser()
                .withName(faker.name().name())
                .withEmail(faker.internet().emailAddress())
                .withGender("helicóptero apache")
                .withStatus(faker.options().option(Status.class))
                .build();
    }

    public static UserRequest createUserWithInvalidStatus(){
        return CreateUserBuilder.aUser()
                .withName(faker.name().name())
                .withEmail(faker.internet().emailAddress())
                .withGender(faker.options().option(Gender.class))
                .withStatus("adormilado")
                .build();
    }

    public static UserRequest createParameterizedUser(String name, String email, String gender, String status){
        return CreateUserBuilder.aUser()
                .withName(name)
                .withEmail(email)
                .withGender(gender)
                .withStatus(status)
                .build();
    }

    public static UserRequest createUserWithEmptyFields(){
        return createParameterizedUser(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }

}
