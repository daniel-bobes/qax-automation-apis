package com.danielbobes.api.gorest.data.factory;

import com.danielbobes.api.gorest.models.comments.CommentRequest;
import com.danielbobes.api.gorest.models.comments.CreateCommentBuilder;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class CommentFactory {

    private static final Faker faker = new Faker(Locale.forLanguageTag("es"));

    private CommentFactory() {

    }

    public static CommentRequest createValidComment(Integer postId) {
        return CreateCommentBuilder.aComment()
                .withPostId(postId)
                .withName(faker.name().name())
                .withEmail(faker.internet().emailAddress())
                .withBody(faker.lorem().paragraph(3))
                .build();
    }

    public static CommentRequest createParameterizedComment(Integer postId, String name, String email, String body) {
        return CreateCommentBuilder.aComment()
                .withPostId(postId)
                .withName(name)
                .withEmail(email)
                .withBody(body)
                .build();
    }

    public static CommentRequest createCommentWithEmptyFields() {
        return createParameterizedComment(null, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
