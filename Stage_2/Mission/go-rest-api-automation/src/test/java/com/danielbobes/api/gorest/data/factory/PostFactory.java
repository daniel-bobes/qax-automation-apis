package com.danielbobes.api.gorest.data.factory;

import com.danielbobes.api.gorest.models.posts.CreatePostBuilder;
import com.danielbobes.api.gorest.models.posts.PostRequest;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public final class PostFactory {

    private static final Faker faker = new Faker(Locale.forLanguageTag("es"));

    private PostFactory() {

    }

    public static PostRequest createValidPost(Integer userId) {
        return CreatePostBuilder.aPost()
                .withUserId(userId)
                .withTitle(faker.book().title())
                .withBody(faker.lorem().paragraph(3))
                .build();
    }

    public static PostRequest createParameterizedPost(Integer userId, String title, String body) {
        return CreatePostBuilder.aPost()
                .withUserId(userId)
                .withTitle(title)
                .withBody(body)
                .build();
    }

    public static PostRequest createPostWithEmptyFields() {
        return createParameterizedPost(null, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
