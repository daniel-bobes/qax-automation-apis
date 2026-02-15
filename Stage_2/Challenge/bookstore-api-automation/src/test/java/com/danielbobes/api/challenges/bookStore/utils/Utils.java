package com.danielbobes.api.challenges.bookStore.utils;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    private static final SecureRandom random = new SecureRandom();

    public static String generateUsername() {
        return "User_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public static String generatePassword() {
        List<Character> passwordCharacters = new ArrayList<>();

        passwordCharacters.add(getRandomCharFrom(Constants.LOWER_CASE_LETTERS));
        passwordCharacters.add(getRandomCharFrom(Constants.UPPER_CASE_LETTERS));
        passwordCharacters.add(getRandomCharFrom(Constants.NUMBERS));
        passwordCharacters.add(getRandomCharFrom(Constants.SPECIAL));

        for (int i = 4; i < 12; i++) {
            passwordCharacters.add(getRandomCharFrom(Constants.ALL));
        }

        Collections.shuffle(passwordCharacters);

        return passwordCharacters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static char getRandomCharFrom(String str) {
        return str.charAt(random.nextInt(str.length()));
    }

}
