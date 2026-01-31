package ua.edu.ukma;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void studentShouldBeCreatedCorrectly() {
        Student s = new Student("Ivanov", "Ivan", "Ivanovych",
                "ST001", 2, "IPZ-21");

        assertEquals(2, s.getCourse());
    }

    @Test
    void fullNameShouldBeCorrect() {
        Student s = new Student("Ivanov", "Ivan", "Ivanovych",
                "1", 1, "G1");

        assertEquals("Ivanov Ivan Ivanovych", s.getFullName());
    }
}


