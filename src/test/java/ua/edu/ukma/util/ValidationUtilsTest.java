package ua.edu.ukma.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class ValidationUtilsTest {
     @Test
     void validateNotEmpty_validString_shouldNotThrow() {
         assertDoesNotThrow(() ->
                 ValidationUtils.validateNotEmpty("Ivan", "Name")
         );
     }

     @Test
     void validateNotEmpty_emptyString_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateNotEmpty("", "Name")
         );
     }

     @Test
     void validateNotEmpty_blankString_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateNotEmpty("   ", "Name")
         );
     }

     @Test
     void validateNotEmpty_null_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateNotEmpty(null, "Name")
         );
     }


     @Test
     void validateCourse_validCourse_shouldNotThrow() {
         assertDoesNotThrow(() ->
                 ValidationUtils.validateCourse(3)
         );
     }

     @Test
     void validateCourse_lessThanOne_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateCourse(0)
         );
     }

     @Test
     void validateCourse_greaterThanSix_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateCourse(7)
         );
     }


     @Test
     void validateGroup_validGroup_shouldNotThrow() {
         assertDoesNotThrow(() ->
                 ValidationUtils.validateGroup(101)
         );
     }

     @Test
     void validateGroup_zero_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateGroup(0)
         );
     }

     @Test
     void validateGroup_negative_shouldThrow() {
         assertThrows(IllegalArgumentException.class, () ->
                 ValidationUtils.validateGroup(-5)
         );
     }
}
