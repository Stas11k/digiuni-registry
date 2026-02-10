package ua.edu.ukma.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class FacultyTest {
     @Test
     void constructor_validData_shouldCreateFaculty() {
         Faculty faculty = new Faculty("Computer Science", "CS");

         assertNotNull(faculty);
         assertEquals("Computer Science", faculty.getName());
         assertEquals("CS", faculty.getShortName());
     }

     @Test
     void constructor_emptyName_shouldThrowException() {
         assertThrows(IllegalArgumentException.class,
                 () -> new Faculty("", "CS"));
     }
     @Test
     void setDean_null_shouldThrowException() {
         Faculty faculty = new Faculty("Computer Science", "CS");

         assertThrows(IllegalArgumentException.class,
                 () -> faculty.setDean(null));
     }

     @Test
     void setContacts_shouldSetContacts() {
         Faculty faculty = new Faculty("Computer Science", "CS");

         faculty.setContacts("cs@ukma.edu.ua");

         assertEquals("cs@ukma.edu.ua", faculty.getContacts());
     }
}
