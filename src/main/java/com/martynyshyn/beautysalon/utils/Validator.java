package com.martynyshyn.beautysalon.utils;

import java.util.stream.Stream;

/**
 * Utility class for validate input fields
 *
 * @author N.Martynyshyn
 */

public final class Validator {

    private static final int REQUIRED_PASS_LENGTH = 13;

    private Validator() {
        //It is necessary that there was no possibility to create an instance of the class
    }

    public static boolean isBlankOrNull(Stream<String> validateStream) {
        return validateStream.anyMatch(str -> str == null || str.isBlank());
    }

    public static boolean noRequiredLength(String password) {
        return password.length() <= REQUIRED_PASS_LENGTH;
    }
}
