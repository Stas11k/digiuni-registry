package ua.edu.ukma.validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeInt {
    int min();
    int max();
    String message() default "Field is out of range";
}