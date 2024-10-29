package com.courseAndStudentManagementSystem.utils;

public class ValidateUtil {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Email inválido!");
        }
    }

    public static void validateNotNullOrEmpty(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
