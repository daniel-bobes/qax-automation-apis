package com.danielbobes.api.bookStore.utils;

public final class Constants {
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE_LETTERS = LOWER_CASE_LETTERS.toUpperCase();
    public static final String NUMBERS = "0123456789";
    public static final String SPECIAL = "@#$%&*!";
    public static final String ALL = UPPER_CASE_LETTERS + LOWER_CASE_LETTERS + NUMBERS + SPECIAL;

    public static final String FAKE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.basura.masbasura";

    private Constants() {}
}
