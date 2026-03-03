package ua.edu.ukma.util;

import ua.edu.ukma.exception.ValidationException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) throw new ValidationException(fieldName + " cannot be empty");
    }

    public static void validateNoDigits(String value, String fieldName) {
        if (value == null) return;
        for (int i = 0; i < value.length(); i++) {
            if (Character.isDigit(value.charAt(i))) throw new ValidationException(fieldName + " cannot contain digits");
        }
    }

    public static void validateCourse(int course) {
        if (course < 1 || course > 6) throw new ValidationException("Course must be between 1 and 6");
    }

    public static void validateGroup(int group) {
        if (group <= 0) throw new ValidationException("Group must be a positive number");
    }
}

