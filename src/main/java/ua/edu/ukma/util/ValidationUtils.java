package ua.edu.ukma.util;

public class ValidationUtils {
    private ValidationUtils() {

    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public static void validateCourse(int course) {
        if (course < 1 || course > 6) {
            throw new IllegalArgumentException(
                    "Course must be between 1 and 6"
            );
        }
    }

    public static void validateGroup(int group) {
        if (group <= 0) {
            throw new IllegalArgumentException(
                    "Group must be a positive number"
            );
        }
    }
}

