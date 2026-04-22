package ua.edu.ukma.validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PositiveNumber {
    String message() default "Field must be positive";
}