package ua.edu.ukma;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void studentShouldBeCreatedCorrectly() {
        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        Department department = new Department("Software Engineering", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);

        Student s = new Student(
                "Ivanov", "Ivan", "Ivanovych",
                "ST001", 2, 2, specialty
        );

        assertEquals(2, s.getCourse());
        assertEquals(specialty, s.getSpecialty());
    }

    @Test
    void fullNameShouldBeCorrect() {
        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        Department department = new Department("Software Engineering", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);

        Student s = new Student(
                "Ivanov", "Ivan", "Ivanovych",
                "1", 1, 1, specialty
        );

        assertEquals("Ivanov Ivan Ivanovych", s.getFullName());
    }
}