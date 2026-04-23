package ua.edu.ukma.domain;

import org.junit.jupiter.api.Test;
import ua.edu.ukma.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeacherTest {

    @Test
    public void teacherShouldBeCreatedCorrectly() {
        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        Department department = new Department("Software Engineering", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);
        Teacher t = new Teacher(
                1, "Bondarenko", "Illia", "Volodymyrovych","Dean",department
        );

        assertEquals(department, t.getDepartment());
        assertEquals("Bondarenko", t.getLastName());
    }

}
