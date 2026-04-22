package ua.edu.ukma.validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NoDigitsField {
    String message() default "Field cannot contain digits";
}