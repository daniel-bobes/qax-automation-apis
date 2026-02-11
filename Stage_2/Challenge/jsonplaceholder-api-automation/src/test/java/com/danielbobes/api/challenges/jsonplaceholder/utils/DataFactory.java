package com.danielbobes.api.challenges.jsonplaceholder.utils;

import com.danielbobes.api.challenges.jsonplaceholder.models.posts.PostRequest;
import com.github.javafaker.Faker;

public class DataFactory {

    private static final Faker FAKER = new Faker();

    public static PostRequest randomPost(Integer userId) {
        String title = FAKER.book().title();
        String body  = FAKER.lorem().sentence(10);
        return new PostRequest(title, body, userId);
    }
}