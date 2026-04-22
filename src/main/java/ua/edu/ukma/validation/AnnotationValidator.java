package ua.edu.ukma.validation;

import ua.edu.ukma.exception.ValidationException;

import java.lang.reflect.Field;

public final class AnnotationValidator {
    private AnnotationValidator() {
    }

    public static void validate(Object target) {
        if (target == null) {
            throw new ValidationException("Object cannot be null");
        }
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(target);
                    validateNotBlank(field, value);
                    validateNoDigits(field, value);
                    validatePositive(field, value);
                    validateRange(field, value);
                } catch (IllegalAccessException e) {
                    throw new ValidationException("Cannot access field: " + field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static void validateNotBlank(Field field, Object value) {
        if (!field.isAnnotationPresent(NotBlankField.class)) return;
        NotBlankField ann = field.getAnnotation(NotBlankField.class);
        if (!(value instanceof String s) || s.isBlank()) {
            throw new ValidationException(field.getName() + ": " + ann.message());
        }
    }

    private static void validateNoDigits(Field field, Object value) {
        if (!field.isAnnotationPresent(NoDigitsField.class)) return;
        if (!(value instanceof String s) || s == null) return;
        NoDigitsField ann = field.getAnnotation(NoDigitsField.class);
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                throw new ValidationException(field.getName() + ": " + ann.message());
            }
        }
    }

    private static void validatePositive(Field field, Object value) {
        if (!field.isAnnotationPresent(PositiveNumber.class)) return;
        if (!(value instanceof Number n)) return;
        PositiveNumber ann = field.getAnnotation(PositiveNumber.class);
        if (n.doubleValue() <= 0) {
            throw new ValidationException(field.getName() + ": " + ann.message());
        }
    }

    private static void validateRange(Field field, Object value) {
        if (!field.isAnnotationPresent(RangeInt.class)) return;
        if (!(value instanceof Integer i)) return;
        RangeInt ann = field.getAnnotation(RangeInt.class);
        if (i < ann.min() || i > ann.max()) {
            throw new ValidationException(field.getName() + ": " + ann.message() + " [" + ann.min() + ".." + ann.max() + "]");
        }
    }
}